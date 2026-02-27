package org.codeit.sb06.team03.mopl.sse.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.sse.infra.in.SseMessage;
import org.codeit.sb06.team03.mopl.sse.infra.out.SseEmitterPort;
import org.codeit.sb06.team03.mopl.sse.infra.out.SseMessagePort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class SseService implements SseUseCase {

    private final SseEmitterPort sseEmitterPort;
    private final SseMessagePort sseMessagePort;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 30; // 30분

    public SseEmitter connect(UUID receiverId, UUID lastEventId) {

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // 연결 종료/타임아웃 시 리포지토리에서 삭제
        emitter.onCompletion(() -> sseEmitterPort.delete(emitter, receiverId));
        emitter.onTimeout(() -> sseEmitterPort.delete(emitter, receiverId));
        emitter.onError((e) -> sseEmitterPort.delete(emitter, receiverId));

        sseEmitterPort.save(emitter, receiverId);

        // 연결되면 더미 이벤트 전송, 연결 확인
        ping(emitter, receiverId, "connect check");

        // 마지막으로 받은 메시지 이후 유실된 메시지 재전송
        if (lastEventId != null) {
            List<SseMessage> missedMessages = sseMessagePort.findAllMissedMessageByUserIdAndIdAfter(receiverId, lastEventId);
            missedMessages.forEach(message ->
                    sendToClient(emitter, receiverId, message.eventName(), message.data(), message.id().toString())
            );
        }

        return emitter;
    }

    @Scheduled(fixedDelay = 15000) // 15초마다 실행
    public void sendHeartbeat() {

        Set<UUID> connectedUsers = sseEmitterPort.findAllConnectedUserIds();
        if (connectedUsers.isEmpty()) return;

        Map<UUID, List<SseEmitter>> allEmitters = sseEmitterPort.findAllByUserIdIn(connectedUsers);

        allEmitters.forEach((userId, emitters) ->
                emitters.forEach(emitter -> ping(emitter, userId, "send heartbeat")));
        log.debug("Sent SSE heartbeat to {} users", connectedUsers.size());
    }

    public void send(Object data, String eventName, UUID receiverId) {
        SseMessage sseMessage = SseMessage.create(eventName, data);
        sseMessagePort.saveMessage(sseMessage, receiverId);

        List<SseEmitter> emitters = sseEmitterPort.findByUserId(receiverId);
        emitters.forEach(emitter -> sendToClient(emitter, receiverId, eventName, data, sseMessage.id().toString()));
    }

    public void sendAll(Map<UUID, Object> objectMap, String eventName) {

        if (objectMap.isEmpty()) return;

        Map<UUID, SseMessage> sseMessageMap = objectMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> SseMessage.create(eventName, entry.getValue())
                        ));
        sseMessagePort.saveMessages(sseMessageMap);

        Set<UUID> receiverIds = sseMessageMap.keySet();
        Map<UUID, List<SseEmitter>> emitterMap = sseEmitterPort.findAllByUserIdIn(receiverIds);

        emitterMap.forEach((userId, emitters) -> {
            SseMessage sseMessage = sseMessageMap.get(userId);
            if (sseMessage != null) {
                emitters.forEach(emitter ->
                        sendToClient(emitter, userId, eventName, sseMessage.data(), sseMessage.id().toString())
                );
            }
        });
    }

    // SseEmitter 객체를 통해 접속중인 모든 사용자에게 이벤트를 전송
    public void broadcast(String eventName, Object data) {
        log.info("Broadcasting event: {}", eventName);
    }

    // 만료된 SseEmitter 삭제
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void cleanUp() {
        log.info("SSE Emitter clean up task started.");

        Set<UUID> userIds = sseEmitterPort.findAllConnectedUserIds();

        int removedCount = 0;
        for (UUID userId : userIds) {
            List<SseEmitter> emitters = sseEmitterPort.findByUserId(userId);

            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("cleanup-ping").data("check"));
                } catch (Exception e) {
                    sseEmitterPort.delete(emitter, userId);
                    removedCount++;
                }
            }
        }

        log.info("SSE Emitter clean up task finished. Removed {} zombie emitters.", removedCount);
    }

    /**
     * 헬퍼 메서드
     */

    private void ping(SseEmitter sseEmitter, UUID userId, String message) {
        sendToClient(sseEmitter, userId, "ping", message, null);
    }

    private void sendToClient(SseEmitter emitter, UUID userId, String eventName, Object data, String eventId) {
        try {
            SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event()
                    .name(eventName)
                    .data(data);
            if (eventId != null && !eventId.isBlank()) {
                eventBuilder.id(eventId);
            }

            emitter.send(eventBuilder);

        } catch (IOException e) {
            sseEmitterPort.delete(emitter, userId);
            log.error("SSE send failed. removing connection: {}", userId);
        }
    }
}
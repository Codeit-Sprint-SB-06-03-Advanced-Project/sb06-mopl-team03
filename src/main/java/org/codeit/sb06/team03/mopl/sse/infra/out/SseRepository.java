package org.codeit.sb06.team03.mopl.sse.infra.out;

import org.codeit.sb06.team03.mopl.sse.infra.in.SseMessage;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * In-Memory Repository
 */
@Repository
public class SseRepository {

    // SseEmitter 저장소
    private final ConcurrentMap<UUID, List<SseEmitter>> emitters = new ConcurrentHashMap<>(); // List로 다중 연결 허용

    // SseMessage 저장소 (이벤트 유실 복원용)
    private final Map<UUID, SseMessage> messages = new ConcurrentHashMap<>();

    /**
     * SseEmitter
     */

    public void saveEmitter(SseEmitter emitter, UUID userId) {
        emitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);
    }

    public Set<UUID> findAllConnectedUserIds() {
        return emitters.keySet();
    }

    public List<SseEmitter> findEmittersByUserId(UUID userId) {
        return emitters.getOrDefault(userId, Collections.emptyList());
    }

    public Map<UUID, List<SseEmitter>> findAllEmittersByUserIdIn(Set<UUID> userIds) {
        return userIds.stream()
                .filter(emitters::containsKey)
                .collect(Collectors.toMap(
                        userId -> userId,
                        userId -> new ArrayList<>(emitters.get(userId))
                ));
    }

    // SSE 연결 종료 시 사용
    public void deleteEmitter(SseEmitter emitter, UUID userId) {
        List<SseEmitter> userEmitters = emitters.get(userId);
        if (userEmitters != null) {
            userEmitters.remove(emitter);
            if (userEmitters.isEmpty()) {
                emitters.remove(userId); // 리스트가 비어있으면 Map에서 삭제
            }
        }
    }

    // 사용자의 모든 SseEmitter 삭제 (로그아웃)
    public void deleteAllEmittersByUserId(UUID userId) {
        emitters.remove(userId);
    }


    /**
     * SseMessage
     */

    public void saveMessage(SseMessage message, UUID userId) {
        messages.put(userId, message);
    }

    public void saveAllMessages(Map<UUID, SseMessage> messagesToSave) {
        messages.putAll(messagesToSave);
    }

    public SseMessage findLastMessageByUserId(UUID userId) {
        return messages.get(userId);
    }

    // 사용자의 모든 SseMessage 삭제 (로그아웃)
    public void deleteAllMessagesByUserId(UUID userId) {
        messages.remove(userId);
    }
}

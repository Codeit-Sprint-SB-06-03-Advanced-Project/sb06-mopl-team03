package org.codeit.sb06.team03.mopl.sse.infra.out;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SseEmitterPort {

    void save(SseEmitter sseEmitter, UUID userId);

    Set<UUID> findAllConnectedUserIds();

    List<SseEmitter> findByUserId(UUID userId);

    Map<UUID, List<SseEmitter>> findAllByUserIdIn(Set<UUID> userIds);

    void delete(SseEmitter sseEmitter, UUID userId);

    void deleteAll(UUID userId);
}

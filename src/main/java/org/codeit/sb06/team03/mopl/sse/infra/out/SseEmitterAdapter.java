package org.codeit.sb06.team03.mopl.sse.infra.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SseEmitterAdapter implements SseEmitterPort {

    private final SseRepository repository;

    @Override
    public void save(SseEmitter sseEmitter, UUID userId) {
        repository.saveEmitter(sseEmitter, userId);
    }

    @Override
    public Set<UUID> findAllConnectedUserIds() {
        return repository.findAllConnectedUserIds();
    }

    @Override
    public List<SseEmitter> findByUserId(UUID userId) {
        return repository.findEmittersByUserId(userId);
    }

    @Override
    public Map<UUID, List<SseEmitter>> findAllByUserIdIn(Set<UUID> userIds) {
        return repository.findAllEmittersByUserIdIn(userIds);
    }

    @Override
    public void delete(SseEmitter sseEmitter, UUID userId) {
        repository.deleteEmitter(sseEmitter, userId);
    }

    @Override
    public void deleteAll(UUID userId) {
        repository.deleteAllEmittersByUserId(userId);
    }
}

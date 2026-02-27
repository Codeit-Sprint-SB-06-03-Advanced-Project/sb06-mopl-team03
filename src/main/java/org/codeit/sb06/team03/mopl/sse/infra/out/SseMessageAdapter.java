package org.codeit.sb06.team03.mopl.sse.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.sse.infra.in.SseMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SseMessageAdapter implements SseMessagePort{

    private final SseRepository repository;

    @Override
    public void saveMessage(SseMessage message, UUID userId) {
        repository.saveMessage(message, userId);
    }

    @Override
    public void saveMessages(Map<UUID, SseMessage> messages) {
        repository.saveAllMessages(messages);
    }

    @Override
    public SseMessage findLastMessageByUserId(UUID userId) {
        return repository.findLastMessageByUserId(userId);
    }

    @Override
    public void deleteAll(UUID userId) {
        repository.deleteAllMessagesByUserId(userId);
    }
}

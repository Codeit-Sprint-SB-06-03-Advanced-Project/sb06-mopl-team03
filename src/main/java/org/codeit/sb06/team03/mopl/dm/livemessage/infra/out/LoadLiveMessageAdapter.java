package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadLiveMessageAdapter implements LoadLiveMessagePort {

    private final LiveMessageRepository liveMessageRepository;

    @Override
    public List<LiveMessage> findAll(
            UUID conversationId,
            String cursor,
            String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    ) {
        return liveMessageRepository.findAll(conversationId, cursor, idAfter, limit + 1, ascending, sortBy);
    }

    @Override
    public long count(UUID conversationId) {
        return liveMessageRepository.count(conversationId);
    }

    @Override
    public Optional<LiveMessage> findById(UUID messageId) {
        return liveMessageRepository.findById(messageId);
    }

    @Override
    public Optional<LiveMessage> findLatestByConversationId(UUID conversationId) {
        return liveMessageRepository.findLatestByConversationId(conversationId);
    }
}

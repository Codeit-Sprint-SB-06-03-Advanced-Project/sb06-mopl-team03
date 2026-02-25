package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadLiveMessageStatPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadLiveMessageStatAdapter implements LoadLiveMessageStatPort {

    private final LiveMessageStatRepository liveMessageStatRepository;

    @Override
    public boolean isActive(UUID conversationId, UUID accountId) {
        return liveMessageStatRepository.isActive(conversationId, accountId);
    }

    @Override
    public List<UUID> findConversationIdsByAccountId(UUID accountId) {
        return liveMessageStatRepository.findConversationIdsByAccountId(accountId);
    }
}

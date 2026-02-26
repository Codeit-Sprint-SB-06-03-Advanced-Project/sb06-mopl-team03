package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetConversationUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadLiveMessageStatPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConversationQueryService implements GetConversationUseCase {

    private final LoadConversationPort loadConversationPort;
    private final LoadLiveMessageStatPort loadLiveMessageStatPort;

    @Override
    public List<Conversation> findAll(
            UUID userId,
            String cursor,
            String idAfter,
            int limit,
            String sortDirection,
            String sortBy
    ) {
        boolean ascending = "ASC".equalsIgnoreCase(sortDirection);
        return loadConversationPort.findAll(userId, cursor, idAfter, limit, ascending, sortBy);
    }

    @Override
    public long countAll(UUID userId) {
        return loadConversationPort.count(userId);
    }

    @Override
    public Conversation findById(UUID userId, UUID conversationId) {
        return loadConversationPort.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId));
    }

    @Override
    public Conversation findByWith(UUID userId, UUID withUserId) {
        return loadConversationPort.findByParticipants(userId, withUserId)
                .orElseThrow(() -> new ConversationNotFoundException(withUserId));
    }

    @Override
    public boolean isParticipantActive(UUID userId, UUID conversationId) {
        return loadLiveMessageStatPort.isActive(conversationId, userId);
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;

import java.util.List;
import java.util.UUID;

public interface GetConversationUseCase {

    List<Conversation> findAll(
            UUID userId,
            String cursor,
            String idAfter,
            int limit,
            String sortDirection,
            String sortBy
    );

    long countAll(UUID userId);

    Conversation findById(UUID userId, UUID conversationId);

    Conversation findByWith(UUID userId, UUID withUserId);

    boolean isParticipantActive(UUID userId, UUID conversationId);
}

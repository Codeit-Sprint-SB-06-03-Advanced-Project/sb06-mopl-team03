package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadConversationPort {

    List<Conversation> findAll(
            UUID userId,
            String cursor,
            String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    );

    long count(UUID userId);

    Optional<Conversation> findById(UUID conversationId);

    Optional<Conversation> findByParticipants(UUID userId, UUID withUserId);
}
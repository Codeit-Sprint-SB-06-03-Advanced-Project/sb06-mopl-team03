package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestConversationDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadConversationPort {

    List<Conversation> findAll(UUID userId, CursorRequestConversationDto request);
    long count(UUID userId);
    Optional<Conversation> findById(UUID userId, UUID conversationId);
    Optional<Conversation> findEntityById(UUID conversationId);
    Optional<Conversation> findByWith(UUID userId, UUID withUserId);
}

package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;

import java.util.UUID;

public interface CreateConversationUseCase {
    Conversation create(UUID userId, CreateConversationCommand command);
}

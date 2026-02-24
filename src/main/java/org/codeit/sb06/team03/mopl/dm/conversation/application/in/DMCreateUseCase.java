package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;

import java.util.UUID;

public interface DMCreateUseCase {
    ConversationDto create(UUID userId, DMConnectCommand command);
}

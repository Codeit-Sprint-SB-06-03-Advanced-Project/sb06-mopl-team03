package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestConversationDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorResponseConversationDto;

import java.util.UUID;

public interface GetConversationUseCase {

    CursorResponseConversationDto findAll(UUID userId, CursorRequestConversationDto request);

    ConversationDto findById(UUID userId, UUID conversationId);
}

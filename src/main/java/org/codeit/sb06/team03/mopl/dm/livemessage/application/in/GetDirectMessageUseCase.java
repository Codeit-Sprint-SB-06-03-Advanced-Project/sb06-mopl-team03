package org.codeit.sb06.team03.mopl.dm.livemessage.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorResponseDirectMessageDto;

import java.util.UUID;

public interface GetDirectMessageUseCase {
    CursorResponseDirectMessageDto getDirectMessage(UUID userId, UUID conversationId, CursorRequestDirectMessageDto request);
}

package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorResponseDirectMessageDto;

import java.util.UUID;

public interface LoadLiveMessagePort {
    CursorResponseDirectMessageDto findAll(UUID userId, UUID withUserId, CursorRequestDirectMessageDto request);
}

package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DMMapper {

    public DMConnectCommand toCommand(String request) {
        final UUID withUserId = UUID.fromString(request);
        return new DMConnectCommand(withUserId);
    }
}

/*
DMConnectCommand : ExistDMPolicy에서 false 시, 생성 후 -> LiveMessageJoinCommand
LiveMessageJoinCommand : LiveMessageStats, activity : true
LiveMessageLeaveCommand : LiveMessageStats, activity : false
MessageReadCommand
LiveMessageStatsChangeCommand
 */
package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import org.codeit.sb06.team03.mopl.dm.conversation.application.in.CreateConversationCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DMMapper {

    public CreateConversationCommand toCommand(String request) {
        final UUID withUserId = UUID.fromString(request);
        return new CreateConversationCommand(withUserId);
    }
}
package org.codeit.sb06.team03.mopl.dm.conversation.domain.exception;

import java.util.UUID;

public class ConversationNotFoundException extends DMException{

    public ConversationNotFoundException(UUID withUserId) {
        super("Not Found : '%s'".formatted(withUserId.toString()));
    }
}

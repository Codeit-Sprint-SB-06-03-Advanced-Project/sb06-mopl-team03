package org.codeit.sb06.team03.mopl.dm.conversation.domain.exception;

import java.util.UUID;

public class ConversationAlreadyExistsException extends DMException{

    public ConversationAlreadyExistsException(UUID withUserId) {
        super("Already Exists : '%s'".formatted(withUserId.toString()));
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.domain.exception;

import java.util.UUID;

public class LiveMessageNotFoundException extends DMException{
    public LiveMessageNotFoundException(UUID messageId) {
        super("Not Found : '%s'".formatted(messageId.toString()));
    }
}

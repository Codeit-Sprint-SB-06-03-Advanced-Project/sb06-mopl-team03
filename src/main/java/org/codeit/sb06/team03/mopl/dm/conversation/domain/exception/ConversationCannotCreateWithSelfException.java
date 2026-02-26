package org.codeit.sb06.team03.mopl.dm.conversation.domain.exception;

import java.util.UUID;

public class ConversationCannotCreateWithSelfException extends DMException{

    public ConversationCannotCreateWithSelfException(UUID withUserId) {
        super("본인은 본인과의 대화방을 만들 수 없습니다. : '%s'".formatted(withUserId.toString()));
    }
}

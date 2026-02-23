package org.codeit.sb06.team03.mopl.liveChat.domain.exception;

import java.util.UUID;

public class LiveChatNotFoundException extends LiveChatException {

    private static final String fromIdFormat = "Live Chat을 찾을 수 없습니다. userId: '%s'";

    public LiveChatNotFoundException(String message) {
        super(message);
    }

    public static LiveChatNotFoundException fromId(UUID id) {
        return new LiveChatNotFoundException(fromIdFormat.formatted(id.toString()));
    }
}

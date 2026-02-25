package org.codeit.sb06.team03.mopl.liveChat.domain.exception;

import java.util.UUID;

public class LiveChatDuplicateException extends LiveChatException {

    private static final String fromIdFormat = "이미 존재하는 LiveChat 입니다. userId: '%s'";

    public LiveChatDuplicateException(String message) {
        super(message);
    }

    public static LiveChatDuplicateException fromId(UUID liveChatId) {
        return new LiveChatDuplicateException(fromIdFormat.formatted(liveChatId));
    }
}

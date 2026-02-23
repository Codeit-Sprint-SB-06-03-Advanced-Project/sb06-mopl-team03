package org.codeit.sb06.team03.mopl.liveChat.domain.exception;

public abstract class LiveChatException extends RuntimeException {

    public LiveChatException(String message) {
        super(message);
    }
}

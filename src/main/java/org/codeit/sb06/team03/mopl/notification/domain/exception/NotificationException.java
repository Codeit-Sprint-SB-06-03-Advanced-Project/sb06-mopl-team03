package org.codeit.sb06.team03.mopl.notification.domain.exception;

public abstract class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }
}

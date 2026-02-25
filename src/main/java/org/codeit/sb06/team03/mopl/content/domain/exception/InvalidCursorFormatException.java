package org.codeit.sb06.team03.mopl.content.domain.exception;

public class InvalidCursorFormatException extends ContentException {

    private static final String messageFormat = "지원하지 않는 Cursor Format입니다. data: '%s'";

    public InvalidCursorFormatException(String message) {
        super(message);
    }

    public static InvalidCursorFormatException from (String data) {
        return new InvalidCursorFormatException(messageFormat.formatted(data));
    }
}

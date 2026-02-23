package org.codeit.sb06.team03.mopl.liveChat.domain.exception;

import org.codeit.sb06.team03.mopl.content.domain.exception.ContentException;

import java.util.UUID;

public class ContentNotFoundException extends ContentException {

    private static final String fromIdFormat = "Content를 찾을 수 없습니다. id: '%s'";

    public ContentNotFoundException(String message) {
        super(message);
    }

    public static ContentNotFoundException fromId(UUID id) {
        return new ContentNotFoundException(fromIdFormat.formatted(id));
    }
}

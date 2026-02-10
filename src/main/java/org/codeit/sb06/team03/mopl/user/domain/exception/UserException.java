package org.codeit.sb06.team03.mopl.user.domain.exception;

import java.io.IOException;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, IOException e) {
        super(message, e);
    }
}

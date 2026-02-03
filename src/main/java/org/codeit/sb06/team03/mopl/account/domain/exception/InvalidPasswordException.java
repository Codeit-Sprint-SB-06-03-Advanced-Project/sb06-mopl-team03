package org.codeit.sb06.team03.mopl.account.domain.exception;

public class InvalidPasswordException extends AccountException {

    public InvalidPasswordException(int minLength) {
        super("Password must be at least %d characters long".formatted(minLength));
    }
}

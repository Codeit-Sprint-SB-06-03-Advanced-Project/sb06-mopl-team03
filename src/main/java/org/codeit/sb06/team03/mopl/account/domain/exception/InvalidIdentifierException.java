package org.codeit.sb06.team03.mopl.account.domain.exception;

public class InvalidIdentifierException extends AccountException {
    public InvalidIdentifierException(String id) {
        super("Invalid identifier : %s".formatted(id));
    }
}

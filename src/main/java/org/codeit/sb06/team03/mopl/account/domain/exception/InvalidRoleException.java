package org.codeit.sb06.team03.mopl.account.domain.exception;

public class InvalidRoleException extends AccountException {

    public InvalidRoleException(String role) {
        super(String.format("Invalid role: '{%s}'", role));
    }
}

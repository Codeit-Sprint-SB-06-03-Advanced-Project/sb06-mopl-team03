package org.codeit.sb06.team03.mopl.account.domain.exception;

public abstract class AccountException extends RuntimeException {

    protected AccountException(String message) {
        super(message);
    }
}

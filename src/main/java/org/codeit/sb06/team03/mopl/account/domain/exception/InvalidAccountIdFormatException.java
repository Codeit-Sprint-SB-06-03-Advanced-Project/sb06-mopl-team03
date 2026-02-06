package org.codeit.sb06.team03.mopl.account.domain.exception;

public class InvalidAccountIdFormatException extends RuntimeException {

    public InvalidAccountIdFormatException(String accountId) {
        super(String.format("Account with id '%s' is invalid", accountId));
    }
}

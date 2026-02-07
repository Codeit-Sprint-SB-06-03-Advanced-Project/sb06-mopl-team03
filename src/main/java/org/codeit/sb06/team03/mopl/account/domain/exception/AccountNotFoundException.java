package org.codeit.sb06.team03.mopl.account.domain.exception;

import java.util.UUID;

public class AccountNotFoundException extends AccountException {

    private static final String MESSAGE_FORMAT = "Account with id '%s' not found";

    public AccountNotFoundException(UUID accountId) {
        super(MESSAGE_FORMAT.formatted(accountId));
    }

    public AccountNotFoundException(String accountId) {
        super(MESSAGE_FORMAT.formatted(accountId));
    }
}

package org.codeit.sb06.team03.mopl.account.domain.exception;

import java.util.UUID;

public class AccountNotFoundException extends AccountException {

    public AccountNotFoundException(UUID accountId) {
        super(String.format("Account with id '%s' not found", accountId));
    }
}

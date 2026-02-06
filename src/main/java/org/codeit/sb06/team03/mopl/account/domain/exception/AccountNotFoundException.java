package org.codeit.sb06.team03.mopl.account.domain.exception;

import java.util.UUID;

public class AccountNotFoundException extends AccountException {

    public AccountNotFoundException(UUID id) {
        super("Account not found: %s".formatted(id));
    }

}

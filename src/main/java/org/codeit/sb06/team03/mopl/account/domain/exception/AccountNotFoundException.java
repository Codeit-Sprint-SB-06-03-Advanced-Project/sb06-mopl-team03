package org.codeit.sb06.team03.mopl.account.domain.exception;

import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

import java.util.UUID;

public class AccountNotFoundException extends AccountException {

    private static final String MESSAGE_FORMAT = "Account with id '%s' not found";
    private static final String EMAIL_MESSAGE_FORMAT = "Account with email '%s' not found";

    public AccountNotFoundException(UUID accountId) {
        super(MESSAGE_FORMAT.formatted(accountId));
    }

    public AccountNotFoundException(String accountId) {
        super(MESSAGE_FORMAT.formatted(accountId));
    }

    public AccountNotFoundException(EmailAddress emailAddress) {
        super(EMAIL_MESSAGE_FORMAT.formatted(emailAddress.value()));
    }
}

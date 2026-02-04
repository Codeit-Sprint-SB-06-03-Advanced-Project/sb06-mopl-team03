package org.codeit.sb06.team03.mopl.account.domain.exception;

public class AccountRegistrationFailedException extends AccountException {

    public AccountRegistrationFailedException(Throwable throwable) {
        super(throwable.getMessage());
    }
}

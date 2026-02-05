package org.codeit.sb06.team03.mopl.account.domain.exception;

public class EmailAddressAlreadyExistsException extends AccountException {

    public EmailAddressAlreadyExistsException(String emailAddress) {
        super("이미 존재하는 이메일입니다: " + emailAddress);
    }
}

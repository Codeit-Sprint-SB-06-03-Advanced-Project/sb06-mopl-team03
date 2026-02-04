package org.codeit.sb06.team03.mopl.account.domain.exception;

public class EmailAlreadyExistsException extends AccountException {

    public EmailAlreadyExistsException(String email) {
        super("이미 존재하는 이메일입니다: " + email);
    }
}

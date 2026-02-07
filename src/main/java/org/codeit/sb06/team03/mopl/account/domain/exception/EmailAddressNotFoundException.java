package org.codeit.sb06.team03.mopl.account.domain.exception;

import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

public class EmailAddressNotFoundException  extends AccountException {

    public EmailAddressNotFoundException(EmailAddress emailAddress) {
        super(emailAddress.value() + "에 해당하는 이메일 계정이 없습니다.");
    }
}

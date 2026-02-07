package org.codeit.sb06.team03.mopl.auth.infra.in;

import org.codeit.sb06.team03.mopl.account.application.in.ResetPasswordCommand;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    // User/infra/in/AccountMapper.java와 네이밍 겹침 이슈.

    public ResetPasswordCommand toCommand(ResetPasswordRequest request) {
        final EmailAddress emailAddress = new EmailAddress(request.email());
        return new ResetPasswordCommand(emailAddress);
    }
}
package org.codeit.sb06.team03.mopl.user.infra.in;

import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.domain.vo.Email;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public RegisterAccountCommand toCommand(UserCreateRequest request) {
        final String name = request.name();
        final Email email = new Email(request.email());
        final String rawPassword = request.password();
        return new RegisterAccountCommand(name, email, rawPassword);
    }
}

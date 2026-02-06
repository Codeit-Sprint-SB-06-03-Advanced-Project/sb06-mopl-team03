package org.codeit.sb06.team03.mopl.user.infra.in;

import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.application.in.AssignRoleCommand;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public RegisterAccountCommand toCommand(UserCreateRequest request) {
        final String name = request.name();
        final EmailAddress emailAddress = new EmailAddress(request.email());
        final String rawPassword = request.password();
        return new RegisterAccountCommand(name, emailAddress, rawPassword);
    }

    public AssignRoleCommand toCommand(UserRoleUpdateRequest request) {
        final String role = request.role();
        return new AssignRoleCommand(role);
    }
}

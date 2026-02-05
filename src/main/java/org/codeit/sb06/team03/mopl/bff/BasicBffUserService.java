package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountUseCase;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.user.infra.in.AccountMapper;
import org.codeit.sb06.team03.mopl.user.infra.in.UserCreateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffUserService implements BffUserService {

    private final AccountMapper accountMapper;
    private final RegisterAccountUseCase registerAccountUseCase;

    @Override
    public UserDto registerAccount(UserCreateRequest request) {
        RegisterAccountCommand command = accountMapper.toCommand(request);
        Account newAccount = registerAccountUseCase.register(command);

        UUID id = newAccount.getId();
        Instant createdAt = newAccount.getCreatedAt();
        String emailAddress = newAccount.getEmailAddress().value();
        String name = request.name();
        String profileImageUrl = null; // TODO
        String role = null; // TODO
        Boolean locked = null; // TODO
        return new UserDto(id, createdAt, emailAddress, name, profileImageUrl, role, locked); // TODO
    }
}

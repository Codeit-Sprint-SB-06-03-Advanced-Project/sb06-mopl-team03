package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.*;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.user.infra.in.*;
import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffUserService implements BffUserService {

    private final AccountMapper accountMapper;
    private final RegisterAccountUseCase registerAccountUseCase;
    private final AssignRoleUseCase assignRoleUseCase;
    private final UpdateLockStatusUseCase updateLockStatusUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Override
    public UserDto registerAccount(UserCreateRequest request) {
        RegisterAccountCommand command = accountMapper.toCommand(request);
        Account newAccount = registerAccountUseCase.register(command);

        UUID id = newAccount.getId();
        Instant createdAt = newAccount.getCreatedAt();
        String emailAddress = newAccount.getEmailAddress().value();
        String name = null; // TODO
        String profileImageUrl = null; // TODO
        String role = null; // TODO
        Boolean locked = null; // TODO
        return new UserDto(id, createdAt, emailAddress, name, profileImageUrl, role, locked); // TODO
    }

    @Override
    public void updatePassword(String userId, PasswordUpdateRequest request) {
        UpdatePasswordCommand command = accountMapper.toCommand(request);
        updatePasswordUseCase.updatePassword(userId, command);
    }


    @Override
    public void assignUserRole(String userId, UserRoleUpdateRequest request) {
        AssignRoleCommand command = accountMapper.toCommand(request);
        assignRoleUseCase.assignRole(userId, command);
    }

    @Override
    public void updateUserLockStatus(UUID userId, UserLockUpdateRequest request) {
        UpdateLockStatusCommand command = accountMapper.toCommand(request);
        updateLockStatusUseCase.updateLocked(userId, command);
    }

    @Override
    public CursorResponseUserDto getUsers(CursorRequestUserDto request) {
        return getAccountUseCase.get(request);
    }

    @Override
    public UserDto getUser(String userId) {
        return getAccountUseCase.get(userId);
    }
}

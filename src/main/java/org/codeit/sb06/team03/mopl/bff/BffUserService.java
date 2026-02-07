package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.*;

import java.util.UUID;

public interface BffUserService {

    UserDto registerAccount(UserCreateRequest request);

    void assignUserRole(UUID userId, UserRoleUpdateRequest request);

    void updateUserLockStatus(UUID userId, UserLockUpdateRequest request);

    void updatePassword(String userId, PasswordUpdateRequest request);

    CursorResponseUserDto getUsers(CursorRequestUserDto request);

    UserDto getUser(String userId);
}

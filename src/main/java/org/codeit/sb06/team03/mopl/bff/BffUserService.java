package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.*;

public interface BffUserService {

    UserDto registerAccount(UserCreateRequest request);

    void assignUserRole(String userId, UserRoleUpdateRequest request);

    void updatePassword(String userId, PasswordUpdateRequest request);

    CursorResponseUserDto getUsers(CursorRequestUserDto request);
}

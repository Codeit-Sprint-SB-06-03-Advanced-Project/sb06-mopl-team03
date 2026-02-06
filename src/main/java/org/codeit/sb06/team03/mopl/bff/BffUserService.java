package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserCreateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.UserRoleUpdateRequest;

public interface BffUserService {

    UserDto registerAccount(UserCreateRequest request);
    void updatePassword(String userId, PasswordUpdateRequest request);
    void assignUserRole(String userId, UserRoleUpdateRequest request);

}

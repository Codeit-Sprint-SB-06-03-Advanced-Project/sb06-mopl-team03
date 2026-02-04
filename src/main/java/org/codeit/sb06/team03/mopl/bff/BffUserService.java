package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.user.infra.in.UserCreateRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;

public interface BffUserService {

    UserDto registerAccount(UserCreateRequest request);
}

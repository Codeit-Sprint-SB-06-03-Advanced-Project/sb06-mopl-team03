package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.auth.infra.in.ResetPasswordRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;

public interface BffAuthService {
    void resetPassword(ResetPasswordRequest request);

    UserDto getUserDto(String accountId);
}

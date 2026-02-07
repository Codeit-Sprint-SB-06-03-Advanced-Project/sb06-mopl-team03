package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.auth.infra.in.ResetPasswordRequest;

public interface BffAuthService {
    void resetPassword(ResetPasswordRequest request);
}

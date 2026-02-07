package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.ResetPasswordCommand;
import org.codeit.sb06.team03.mopl.account.application.in.ResetPasswordUseCase;
import org.codeit.sb06.team03.mopl.auth.infra.in.AuthMapper;
import org.codeit.sb06.team03.mopl.auth.infra.in.ResetPasswordRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BasicBffAuthService implements BffAuthService{

    private final AuthMapper authMapper;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        ResetPasswordCommand command = authMapper.toCommand(request);
        resetPasswordUseCase.resetPassword(command);
    }
}

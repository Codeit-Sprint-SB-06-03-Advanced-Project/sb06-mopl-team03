package org.codeit.sb06.team03.mopl.account.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.codeit.sb06.team03.mopl.account.domain.policy.PasswordEncryptionPolicy;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordResetService {

    private final PasswordEncryptionPolicy passwordEncryptionPolicy;

    public void validateTempPassword(PasswordReset passwordReset, String tempPassword) {
        Password encryptedInput = passwordEncryptionPolicy.apply(tempPassword);

        if (!passwordReset.validateTempPassword(encryptedInput)) {
            throw new RuntimeException(); // TODO 커스텀예외
        }
    }

}

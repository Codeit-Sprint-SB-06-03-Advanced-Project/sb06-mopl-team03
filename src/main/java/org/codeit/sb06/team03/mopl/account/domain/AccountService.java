package org.codeit.sb06.team03.mopl.account.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.policy.PasswordEncryptionPolicy;
import org.codeit.sb06.team03.mopl.account.domain.policy.TempPasswordResetTimeoutPolicy;
import org.codeit.sb06.team03.mopl.account.domain.policy.TempPasswordGenerationPolicy;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final PasswordEncryptionPolicy passwordEncryptionPolicy;
    private final TempPasswordGenerationPolicy tempPasswordGenerationPolicy;
    private final TempPasswordResetTimeoutPolicy tempPasswordResetTimeoutPolicy;

    public Account create(EmailAddress emailAddress, String rawPassword) {
        Password password = passwordEncryptionPolicy.apply(rawPassword);
        return Account.create(emailAddress, password);
    }

    public Account updateRole(Account account, Role role) {
        return account.updateRole(role);
    }

    public Account resetPassword(Account account) {
        Password tempPassword = tempPasswordGenerationPolicy.generate();
        Instant expiresAt = tempPasswordResetTimeoutPolicy.get();
        return account.passwordReset(tempPassword, expiresAt);
    }
}

package org.codeit.sb06.team03.mopl.account.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.policy.PasswordEncryptionPolicy;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final PasswordEncryptionPolicy passwordEncryptionPolicy;

    public Account create(EmailAddress emailAddress, String rawPassword) {
        Password password = passwordEncryptionPolicy.apply(rawPassword);
        return Account.create(emailAddress, password);
    }

    public void updatePassword(Account account, String newPassword) {
        Password password = passwordEncryptionPolicy.apply(newPassword);
        account.updatePassword(password);
    }

    public Account updateRole(Account account, Role role) {
        return account.updateRole(role);
    }

    public Account updateLocked(Account account, boolean locked) {
        return account.updateLocked(locked);
    }
}

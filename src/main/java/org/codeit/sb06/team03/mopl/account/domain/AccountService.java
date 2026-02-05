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
}

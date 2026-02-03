package org.codeit.sb06.team03.mopl.account.domain.policy;

import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mopl.account.password-policy", havingValue = "no")
public class PasswordNoEncryptionPolicy implements PasswordEncryptionPolicy {

    @Override
    public Password apply(String rawPassword) {
        return new Password(rawPassword);
    }
}

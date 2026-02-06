package org.codeit.sb06.team03.mopl.account.domain.policy;

import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mopl.account.password-policy", havingValue = "no")
public class PasswordNoEncryptionPolicy implements PasswordEncryptionPolicy {

    @Override
    public Password apply(String rawPassword) {
        // TODO : security 도입 시, 아래 주석 코드 예제 참고
        // return new Password(encrypt(rawPassword), rawPassword);
        return new Password(rawPassword, rawPassword);
    }
}

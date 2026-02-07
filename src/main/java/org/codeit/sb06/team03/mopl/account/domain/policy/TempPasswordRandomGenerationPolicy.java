package org.codeit.sb06.team03.mopl.account.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
/*import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;*/

@RequiredArgsConstructor
@Component
public class TempPasswordRandomGenerationPolicy implements TempPasswordGenerationPolicy{

    /*private static final String UPPER = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijkmnopqrstuvwxyz";
    private static final String DIGIT = "23456789";
    private static final String SPECIAL = "!@#$%^&*";
    private static final String ALL_CHARS = UPPER + LOWER + DIGIT + SPECIAL;
    private static final int PASSWORD_LENGTH = 8;

    private static final SecureRandom random = new SecureRandom();*/

    private final PasswordEncryptionPolicy encryptionPolicy;

    @Override
    public String generate() {
        /*
          Security는 .. ? 자동 생성
           -> 이유는 보안적인 사항은 개별적으로 개발하는 것이 아닌 security에서 생성하는대로 사용하는 것.
          그런데 여기서 보안적인 요소라긴 하지만 시큐리티에서 생성하는 것이 아닌 암호화/복호화 또는 인증/인가의 영역을 다뤄야 하는 것이 아닌가?
           -> 때문에 생성되는 것까지는 하고, 저장이나 반출될 떄 암호화/복호화하면 되지 않을까라고 생각 중입니다.
          *일단은 "temporary1!!"로.*
         */
        /*List<String> passwordChars = new ArrayList<>();
        passwordChars.add(String.valueOf(UPPER.charAt(random.nextInt(UPPER.length()))));
        passwordChars.add(String.valueOf(LOWER.charAt(random.nextInt(LOWER.length()))));
        passwordChars.add(String.valueOf(DIGIT.charAt(random.nextInt(DIGIT.length()))));
        passwordChars.add(String.valueOf(SPECIAL.charAt(random.nextInt(SPECIAL.length()))));

        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            passwordChars.add(String.valueOf(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()))));
        }

        Collections.shuffle(passwordChars);*/
        final String rawPassword = "temporary1!!";
        return rawPassword;
    }
}

package org.codeit.sb06.team03.mopl.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    @ConditionalOnProperty(name = "mopl.account.password-policy", havingValue = "no")
    public PasswordEncoder temporaryPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @ConditionalOnProperty(name = "mopl.account.password-policy", havingValue = "bcrypt")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

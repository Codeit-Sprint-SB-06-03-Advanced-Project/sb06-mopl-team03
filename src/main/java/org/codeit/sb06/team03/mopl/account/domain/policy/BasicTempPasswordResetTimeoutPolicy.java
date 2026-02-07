package org.codeit.sb06.team03.mopl.account.domain.policy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class BasicTempPasswordResetTimeoutPolicy implements TempPasswordResetTimeoutPolicy {

    private final Duration timeout;

    /**
     * @return : Instant(3min)
     */
    @Override
    public Instant createExpiresAt() {
        return Instant.now().plus(timeout);
    }

    public BasicTempPasswordResetTimeoutPolicy(
            @Value("${mopl.account.temp-password-reset-timeout-policy-in-minutes}")
            long timeoutInMinutes
    ) {
        Duration timeout = Duration.ofMinutes(timeoutInMinutes);
        this.timeout = timeout;
    }
}

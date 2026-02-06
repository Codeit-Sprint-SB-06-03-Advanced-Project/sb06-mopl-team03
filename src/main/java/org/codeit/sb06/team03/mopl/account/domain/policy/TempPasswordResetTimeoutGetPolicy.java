package org.codeit.sb06.team03.mopl.account.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "mopl.account.temp-password-reset-timeout-policy", havingValue = "get")
public class TempPasswordResetTimeoutGetPolicy implements TempPasswordResetTimeoutPolicy {
    /**
     * @return : Instant(3min)
     */
    @Override
    public Instant get() {
        return Instant.now().plusSeconds(60 * 3);
    }
}

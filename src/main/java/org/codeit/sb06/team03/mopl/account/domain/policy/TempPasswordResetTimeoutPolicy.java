package org.codeit.sb06.team03.mopl.account.domain.policy;

import java.time.Instant;

public interface TempPasswordResetTimeoutPolicy {
    Instant createExpiresAt();
}

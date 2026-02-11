package org.codeit.sb06.team03.mopl.common.security.jwt;

import java.time.Instant;
import java.util.UUID;

public record TokenResult(
        String token,
        UUID id,
        Instant expiresAt
) {
}

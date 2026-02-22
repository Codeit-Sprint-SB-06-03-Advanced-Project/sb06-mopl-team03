package org.codeit.sb06.team03.mopl.common.security.jwt;

import java.util.UUID;

public record JwtClaims (
        UUID id,
        String email,
        String name,
        String profileImageUrl,
        String role
) {
}

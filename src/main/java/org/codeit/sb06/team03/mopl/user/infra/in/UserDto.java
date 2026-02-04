package org.codeit.sb06.team03.mopl.user.infra.in;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
        UUID id,
        Instant createdAt,
        String email,
        String name,
        String profileImageUrl,
        String role,
        Boolean locked
) {
}

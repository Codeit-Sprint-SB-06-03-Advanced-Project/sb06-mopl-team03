package org.codeit.sb06.team03.mopl.playlist.infra.in.response;

import java.util.UUID;

public record UserSummaryDto (
        UUID userId,
        String name,
        String profileImageUrl
) {
}

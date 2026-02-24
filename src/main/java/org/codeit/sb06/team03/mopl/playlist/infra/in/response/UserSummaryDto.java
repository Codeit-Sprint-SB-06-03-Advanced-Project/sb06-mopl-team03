package org.codeit.sb06.team03.mopl.playlist.infra.in.response;

import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.UUID;

public record UserSummaryDto (
        UUID userId,
        String name,
        String profileImageUrl
) {
    public static UserSummaryDto from(Profile profile) {

        String profileImageUrl = (profile.getTimeoutImage() != null) ? profile.getTimeoutImage().getPresignedUrl() : null;

        return new UserSummaryDto(
                profile.getAccountId(),
                profile.getName(),
                profileImageUrl
        );
    }
}

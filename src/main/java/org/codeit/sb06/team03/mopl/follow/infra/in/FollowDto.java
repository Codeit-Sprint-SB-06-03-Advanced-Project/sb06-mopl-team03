package org.codeit.sb06.team03.mopl.follow.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record FollowDto(
        @Schema(description = "팔로우 ID")
        UUID id,

        @Schema(description = "팔로우 대상 사용자 ID", format = "uuid")
        UUID followeeId,

        @Schema(description = "팔로워 사용자 ID", format = "uuid")
        UUID followerId
) {
}

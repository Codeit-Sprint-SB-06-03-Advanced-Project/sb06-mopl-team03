package org.codeit.sb06.team03.mopl.follow.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.UUID;

public record FollowDto(
        @Schema(description = "팔로우 ID", format = "uuid")
        @UUID
        String id,

        @Schema(description = "팔로우 대상 사용자 ID", format = "uuid")
        @UUID
        String followeeId,

        @Schema(description = "팔로워 사용자 ID", format = "uuid")
        @UUID
        String followerId
) {
}

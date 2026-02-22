package org.codeit.sb06.team03.mopl.follow.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record FollowRequest(
        @Schema(description = "팔로우 대상 사용자 ID", format = "uuid")
        @NotNull @UUID
        String followeeId) {
}

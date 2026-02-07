package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CursorRequestUserDto(
        @Schema(description = "이메일")
        String emailLike,

        @Schema(description = "권한", allowableValues = {"ADMIN", "USER"})
        @Pattern(regexp = "^(ADMIN|USER)$")
        String roleEqual,

        @Schema(description = "계정 장금 상태")
        Boolean isLocked,

        @Schema(description = "커서")
        String cursor,

        @Schema(description = "보조 커서", format = "uuid")
        String idAfter,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "한 번에 가져올 개수")
        @NotNull
        Integer limit,

        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "정렬 방향",
                allowableValues = {"ASCENDING", "DESCENDING"}
        )
        @NotNull
        @Pattern(regexp = "^(ASCENDING|DESCENDING)$")
        String sortDirection,

        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "정렬 기준",
                allowableValues = {"name", "email", "createdAt", "isLocked", "role"}
        )
        @NotNull
        @Pattern(regexp = "^(name|email|createdAt|isLocked|role)$")
        String sortBy
) {
}

package org.codeit.sb06.team03.mopl.notification.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CursorRequestNotificationDto (

        @Schema(description = "커서")
        String cursor,

        @Schema(description = "보조 커서")
        String idAfter,

        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "한 번에 가져올 개수"
        )
        @NotNull
        @Min(1)
        Integer limit,

        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "정렬 방향",
                allowableValues = {"ASCENDING", "DESCENDING"}
        )
        @NotBlank
        @Pattern(regexp = "^(ASCENDING|DESCENDING)$")
        String sortDirection,

        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "정령 기준",
                allowableValues = {"createdAt"}
        )
        @NotBlank
        @Pattern(regexp = "^(createdAt)$")
        String sortBy
) {
}

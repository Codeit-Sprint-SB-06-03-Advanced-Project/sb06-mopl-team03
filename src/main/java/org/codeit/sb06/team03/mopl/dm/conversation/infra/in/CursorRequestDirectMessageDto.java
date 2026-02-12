package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CursorRequestDirectMessageDto(
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
                allowableValues = {"createdAt"}
        )
        @NotNull
        @Pattern(regexp = "^(createdAt)$")
        String sortBy
) {
}

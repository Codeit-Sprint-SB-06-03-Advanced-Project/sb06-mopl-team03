package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import org.codeit.sb06.team03.mopl.content.domain.exception.InvalidCursorFormatException;
import org.hibernate.validator.constraints.UUID;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public record WatchingSessionCursorRequest(
        @Schema(description = "시청자 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String watcherNameLike,

        @Schema(description = "커서", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String cursor,

        @Schema(description = "보조 커서", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @UUID(message = "잘못된 UUID 형식입니다.")
        String idAfter,

        @Schema(description = "한 번에 가져올 개수", requiredMode = Schema.RequiredMode.REQUIRED)
        @Min(value = 1)
        int limit,

        @Schema(
                description = "정렬 방향",
                defaultValue = "ASCENDING",
                allowableValues = {"ASCENDING", "DESCENDING"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String sortDirection,

        @Schema(
                description = "정렬 기준",
                defaultValue = "createdAt",
                allowableValues = "createdAt",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String sortBy
) {
    public WatchingSessionCursorRequest {
        if (sortDirection == null) {
            sortDirection = "ASCENDING";
        }
        if (sortBy == null) {
            sortBy = "createdAt";
        }
        if (cursor != null) {
            try {
                Instant.parse(cursor);
            } catch (DateTimeParseException e) {
                throw InvalidCursorFormatException.from(cursor);
            }
        }
    }
}
package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.List;

public record CursorResponseUserDto(

        @Schema(description = "데이터 목록")
        List<UserDto> data,

        @Nullable
        @Schema(description = "다음 커서")
        String nextCursor,

        @Nullable
        @Schema(description = "다음 요청의 보조 커서")
        String nextIdAfter,

        @Schema(description = "다음 데이터가 있는지 여부")
        Boolean hasNext,

        @Schema(description = "총 데이터 개수")
        Long totalCount,

        @Schema(description = "정렬 기준")
        String sortBy,

        @Schema(description = "정렬 방향")
        SortOrder sortOrder
        ) {

    public enum SortOrder {
        ASCENDING, DESCENDING;

        public static SortOrder parse(String name) {
            return SortOrder.valueOf(name.toUpperCase());
        }
    }
}

package org.codeit.sb06.team03.mopl.playlist.infra.in.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.List;

public record CursorResponsePlaylistDto (

        @Schema(description = "데이터 목록")
        List<PlaylistDto> data,

        @Nullable
        @Schema(description = "다음 커서")
        String nextCursor,

        @Nullable
        @Schema(description = "다음 요청의 보조 커서")
        String nextIdAfter,

        @Schema(description = "다음 데이터가 있는지 여부")
        boolean hasNext,

        @Schema(description = "총 데이터 개수")
        long totalCount,

        @Schema(description = "정렬 기준")
        String sortBy,

        @Schema(description = "정렬 방향")
        SortOrder sortDirection
) {
    public enum SortOrder {
        ASCENDING, DESCENDING;
    }
}

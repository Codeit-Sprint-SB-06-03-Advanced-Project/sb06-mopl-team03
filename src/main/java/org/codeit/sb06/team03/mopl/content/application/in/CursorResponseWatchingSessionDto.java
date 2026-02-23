package org.codeit.sb06.team03.mopl.content.application.in;

import org.codeit.sb06.team03.mopl.common.SessionDetails;

import java.util.List;

public record CursorResponseWatchingSessionDto(

        List<SessionDetails> data,
        String nextCursor,
        String nextIdAfter,
        boolean hasNext,
        long totalCount,
        String sortDirection,
        String sortBy
) {

}

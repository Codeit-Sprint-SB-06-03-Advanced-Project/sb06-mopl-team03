package org.codeit.sb06.team03.mopl.content.infra.in.mock;

import org.codeit.sb06.team03.mopl.common.ContentResult;

import java.util.List;

public record ContentCursorResponse(
        List<ContentResult> data,
        boolean hasNext,
        String sortDirection,
        String sortBy,
        long totalCount
) {
}

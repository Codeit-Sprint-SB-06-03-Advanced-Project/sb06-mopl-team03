package org.codeit.sb06.team03.mopl.content.infra.in.mock;

public record ContentCursorRequest(
        int limit,
        String sortDirection,
        String sortBy
) {
}

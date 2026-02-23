package org.codeit.sb06.team03.mopl.content.application.out;

import java.time.Instant;
import java.util.UUID;

public record WatchingSessionCursorQuery(

        UUID contentId,
        String watcherNameLike,
        Instant cursor,
        UUID idAfter,
        int limit,
        String sortDirection,
        String sortBy
) {
}

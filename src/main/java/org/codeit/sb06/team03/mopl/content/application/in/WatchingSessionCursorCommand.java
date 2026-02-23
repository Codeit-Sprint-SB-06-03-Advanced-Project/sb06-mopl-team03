package org.codeit.sb06.team03.mopl.content.application.in;

public record WatchingSessionCursorCommand(

        String contentId,
        String watcherNameLike,
        String cursor,
        String idAfter,
        int limit,
        String sortDirection,
        String sortBy
) {
}

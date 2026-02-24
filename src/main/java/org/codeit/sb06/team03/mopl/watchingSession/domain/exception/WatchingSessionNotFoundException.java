package org.codeit.sb06.team03.mopl.watchingSession.domain.exception;

import java.util.UUID;

public class WatchingSessionNotFoundException extends WatchingSessionException {

    private static final String fromLiveChatIdAndWatcherIdFormat =
            "Watching Session을 찾을 수 없습니다. liveChatId: '%s', watcherId: '%s'";

    private static final String fromWatcherIdFormat
            = "Watching Session을 찾을 수 없습니다. watcherId: '%s'";

    public WatchingSessionNotFoundException(String message) {
        super(message);
    }

    public static WatchingSessionNotFoundException fromLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId) {
        return new WatchingSessionNotFoundException(
                fromLiveChatIdAndWatcherIdFormat.formatted(liveChatId, watcherId)
        );
    }

    public static WatchingSessionNotFoundException fromWatcherId(UUID watcherId) {
        return new WatchingSessionNotFoundException(
                fromWatcherIdFormat.formatted(watcherId)
        );
    }
}

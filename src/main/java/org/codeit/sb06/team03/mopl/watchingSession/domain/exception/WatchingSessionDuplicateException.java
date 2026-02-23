package org.codeit.sb06.team03.mopl.watchingSession.domain.exception;

import java.util.UUID;

public class WatchingSessionDuplicateException extends WatchingSessionException {

    private static final String fromLiveChatIdAndAccountIdFormat
            = "중복된 WatchingSession입니다. liveChatId: '%s', accountId: '%s'";

    public WatchingSessionDuplicateException(String message) {
        super(message);
    }

    public static WatchingSessionDuplicateException fromLiveChatIdAndAccountId(UUID liveChatId, UUID accountId) {
        return new WatchingSessionDuplicateException(
                fromLiveChatIdAndAccountIdFormat.formatted(liveChatId, accountId)
        );
    }
}

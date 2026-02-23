package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import org.codeit.sb06.team03.mopl.common.SessionDetails;

public record LiveChatPresenceResponse(
        String type,
        SessionDetails watchingSession,
        long watcherCount
) {
}

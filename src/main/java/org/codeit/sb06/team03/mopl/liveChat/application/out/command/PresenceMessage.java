package org.codeit.sb06.team03.mopl.liveChat.application.out.command;

import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.UserSummary;

import java.time.Instant;
import java.util.UUID;

public record PresenceMessage(
        UserSummary userSummary,
        UUID watchingSessionId,
        Instant watchingSessionCreatedAt,
        int count,
        String type,
        String destination,
        ContentResult contentResult
) {
}

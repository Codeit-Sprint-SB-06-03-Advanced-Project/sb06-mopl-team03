package org.codeit.sb06.team03.mopl.watchingSession.application.in;

import java.util.UUID;

public record CreateWatchingSessionCommand(
        UUID liveChatId,
        UUID watcherId
) {
}

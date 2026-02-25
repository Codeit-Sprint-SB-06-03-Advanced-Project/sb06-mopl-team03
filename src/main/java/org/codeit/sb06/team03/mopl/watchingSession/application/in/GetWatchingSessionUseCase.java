package org.codeit.sb06.team03.mopl.watchingSession.application.in;

import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;

import java.util.List;
import java.util.UUID;

public interface GetWatchingSessionUseCase {

    List<WatchingSession> get(UUID watcherId);

    WatchingSession get(UUID liveChatId, UUID watcherId);
}

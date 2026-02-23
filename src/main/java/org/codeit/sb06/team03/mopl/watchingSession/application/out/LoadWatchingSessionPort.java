package org.codeit.sb06.team03.mopl.watchingSession.application.out;

import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadWatchingSessionPort {

    boolean existsByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId);

    List<WatchingSession> findByWatcherId(UUID watcherId);

    Optional<WatchingSession> findByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId);
}

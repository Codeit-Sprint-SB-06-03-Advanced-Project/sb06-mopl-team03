package org.codeit.sb06.team03.mopl.watchingSession.infra.out;

import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WatchingSessionRepository extends JpaRepository<WatchingSession, UUID> {

    boolean existsByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId);

    void deleteByWatcherId(UUID watcherId);

    Optional<WatchingSession> findByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId);

    int countByLiveChatId(UUID liveChatId);

    List<WatchingSession> findByWatcherId(UUID watcherId);
}

package org.codeit.sb06.team03.mopl.watchingSession.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.watchingSession.application.out.LoadWatchingSessionPort;
import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoadWatchingSessionAdapter implements LoadWatchingSessionPort {

    private final WatchingSessionRepository watchingSessionRepository;

    @Override
    public boolean existsByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId) {
        return watchingSessionRepository.existsByLiveChatIdAndWatcherId(liveChatId, watcherId);
    }

    @Override
    public List<WatchingSession> findByWatcherId(UUID watcherId) {
        return watchingSessionRepository.findByWatcherId(watcherId);
    }

    @Override
    public Optional<WatchingSession> findByLiveChatIdAndWatcherId(UUID liveChatId, UUID watcherId) {
        return watchingSessionRepository.findByLiveChatIdAndWatcherId(liveChatId, watcherId);
    }
}

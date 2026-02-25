package org.codeit.sb06.team03.mopl.watchingSession.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.watchingSession.application.out.SaveWatchingSessionPort;
import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveWatchingSessionAdapter implements SaveWatchingSessionPort {

    private final WatchingSessionRepository watchingSessionRepository;

    @Override
    public WatchingSession save(WatchingSession watchingSession) {
        return watchingSessionRepository.save(watchingSession);
    }
}

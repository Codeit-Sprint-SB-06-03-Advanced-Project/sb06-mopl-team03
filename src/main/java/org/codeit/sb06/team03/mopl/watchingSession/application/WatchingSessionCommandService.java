package org.codeit.sb06.team03.mopl.watchingSession.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.CreateWatchingSessionCommand;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.CreateWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.DeleteWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.GetWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.application.out.DeleteWatchingSessionPort;
import org.codeit.sb06.team03.mopl.watchingSession.application.out.LoadWatchingSessionPort;
import org.codeit.sb06.team03.mopl.watchingSession.application.out.SaveWatchingSessionPort;
import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.codeit.sb06.team03.mopl.watchingSession.domain.exception.WatchingSessionDuplicateException;
import org.codeit.sb06.team03.mopl.watchingSession.domain.exception.WatchingSessionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class WatchingSessionCommandService implements
        CreateWatchingSessionUseCase, DeleteWatchingSessionUseCase, GetWatchingSessionUseCase {

    private final SaveWatchingSessionPort saveWatchingSessionPort;
    private final LoadWatchingSessionPort loadWatchingSessionPort;
    private final DeleteWatchingSessionPort deleteWatchingSessionPort;

    @Override
    @Transactional
    public void create(CreateWatchingSessionCommand command) {
        UUID liveChatId = command.liveChatId();
        UUID watcherId = command.watcherId();

        if (loadWatchingSessionPort.existsByLiveChatIdAndWatcherId(liveChatId, watcherId)) {
            throw WatchingSessionDuplicateException.fromLiveChatIdAndAccountId(liveChatId, watcherId);
        }

        WatchingSession watchingSession = WatchingSession.create(watcherId, liveChatId);
        saveWatchingSessionPort.save(watchingSession);
    }

    @Override
    @Transactional
    public void deleteByWatcherId(UUID watcherId) {
        deleteWatchingSessionPort.deleteByWatcherId(watcherId);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        deleteWatchingSessionPort.deleteById(id);
    }

    @Override
    public List<WatchingSession> get(UUID watcherId) {
        List<WatchingSession> watchingSessions = loadWatchingSessionPort.findByWatcherId(watcherId);
        if (watchingSessions.isEmpty()) {
            throw WatchingSessionNotFoundException.fromWatcherId(watcherId);
        }

        return watchingSessions;
    }

    @Override
    public WatchingSession get(UUID liveChatId, UUID watcherId) {
        return loadWatchingSessionPort.findByLiveChatIdAndWatcherId(liveChatId, watcherId)
                .orElseThrow(() -> WatchingSessionNotFoundException.fromLiveChatIdAndWatcherId(liveChatId, watcherId));
    }


}

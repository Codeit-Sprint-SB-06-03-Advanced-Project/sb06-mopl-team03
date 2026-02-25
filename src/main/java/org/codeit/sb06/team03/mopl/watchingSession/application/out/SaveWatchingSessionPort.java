package org.codeit.sb06.team03.mopl.watchingSession.application.out;

import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;

public interface SaveWatchingSessionPort {

    WatchingSession save(WatchingSession watchingSession);
}

package org.codeit.sb06.team03.mopl.watchingSession.application.out;

import java.util.UUID;

public interface DeleteWatchingSessionPort {

    void deleteByWatcherId(UUID watcherId);

    void deleteById(UUID id);
}

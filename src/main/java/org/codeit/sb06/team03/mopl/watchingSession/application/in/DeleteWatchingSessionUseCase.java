package org.codeit.sb06.team03.mopl.watchingSession.application.in;

import java.util.UUID;

public interface DeleteWatchingSessionUseCase {

    void deleteByWatcherId(UUID watcherId);

    void delete(UUID id);
}

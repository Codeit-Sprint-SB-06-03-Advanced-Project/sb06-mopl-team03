package org.codeit.sb06.team03.mopl.watchingSession.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class WatchingSessionEvent {

    @Getter
    @RequiredArgsConstructor
    public static final class WatchingSessionCreateEvent extends WatchingSessionEvent {

        private final UUID accountId;
        private final UUID watchingSessionId;
    }
}

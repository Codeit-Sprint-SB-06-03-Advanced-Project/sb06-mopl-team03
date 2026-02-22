package org.codeit.sb06.team03.mopl.playlist.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class PlaylistEvent {

    @Getter
    @RequiredArgsConstructor
    public static final class PlaylistCreatedEvent extends PlaylistEvent {
        private final UUID ownerId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class SubscriptionCreatedEvent extends PlaylistEvent {
        private final UUID playlistId;
        private final UUID subscriberId;
        private final UUID ownerId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class CurationAddedEvent extends PlaylistEvent {
        private final UUID playlistId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class PlaylistDeletedEvent extends PlaylistEvent {
        private final UUID playlistId;
    }
}

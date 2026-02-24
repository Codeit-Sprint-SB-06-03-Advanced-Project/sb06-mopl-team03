package org.codeit.sb06.team03.mopl.follow.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public abstract sealed class FollowEvent {

    public static final class FolloweeCreatedEvent extends FollowEvent {
    }

    @Getter
    @RequiredArgsConstructor
    public static final class FollowedEvent extends FollowEvent {
        private final UUID followeeId;
        private final UUID followerId;
    }

    public static final class UnfollowedEvent extends FollowEvent {
    }
}

package org.codeit.sb06.team03.mopl.follow.domain.event;

public abstract sealed class FollowEvent {

    public static final class FolloweeCreatedEvent extends FollowEvent {
    }

    public static final class FollowedEvent extends FollowEvent {
    }

    public static final class UnfollowedEvent extends FollowEvent {
    }
}

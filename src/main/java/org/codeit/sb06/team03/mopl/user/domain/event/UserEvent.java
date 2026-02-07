package org.codeit.sb06.team03.mopl.user.domain.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class UserEvent {

    public static final class UserProfileCreatedEvent extends UserEvent {
    }

    public static final class UserProfileUpdatedEvent extends UserEvent {
    }

    public static final class FollowedEvent extends UserEvent {
    }

    public static final class UnfollowedEvent extends UserEvent {
    }
}

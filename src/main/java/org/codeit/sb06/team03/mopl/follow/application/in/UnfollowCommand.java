package org.codeit.sb06.team03.mopl.follow.application.in;

import java.util.UUID;

public record UnfollowCommand(UUID followerId, UUID unfollowId) {
}

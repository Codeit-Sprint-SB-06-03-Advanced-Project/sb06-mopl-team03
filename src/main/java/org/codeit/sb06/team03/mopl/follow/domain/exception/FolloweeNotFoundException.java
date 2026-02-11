package org.codeit.sb06.team03.mopl.follow.domain.exception;

import java.util.UUID;

public class FolloweeNotFoundException extends FollowException {

    public FolloweeNotFoundException(UUID followeeId) {
        super("Followee not found: " + followeeId);
    }
}

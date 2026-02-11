package org.codeit.sb06.team03.mopl.follow.application.out;

import org.codeit.sb06.team03.mopl.follow.domain.Followee;

import java.util.Optional;
import java.util.UUID;

public interface LoadFolloweePort {
    Optional<Followee> findById(UUID followeeId);
}

package org.codeit.sb06.team03.mopl.follow.application.out;

import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.codeit.sb06.team03.mopl.follow.domain.entity.Follower;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadFolloweePort {
    Optional<Followee> findById(UUID followeeId);
}

package org.codeit.sb06.team03.mopl.follow.application.in;

import org.codeit.sb06.team03.mopl.follow.domain.Followee;

import java.util.Optional;
import java.util.UUID;

public interface GetFolloweeUseCase {

    Optional<Followee> findById(UUID id);
}

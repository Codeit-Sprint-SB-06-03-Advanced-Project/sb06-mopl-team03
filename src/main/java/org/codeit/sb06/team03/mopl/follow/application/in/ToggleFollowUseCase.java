package org.codeit.sb06.team03.mopl.follow.application.in;

import org.codeit.sb06.team03.mopl.follow.infra.in.FollowDto;

public interface ToggleFollowUseCase {

    FollowDto follow(FollowCommand command);

    void unfollow(UnfollowCommand command);
}

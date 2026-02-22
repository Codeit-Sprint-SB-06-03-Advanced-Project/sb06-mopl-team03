package org.codeit.sb06.team03.mopl.follow.application.in;

import org.codeit.sb06.team03.mopl.follow.domain.Followee;

public interface CreateFollowUseCase {

    Followee create(CreateFollowCommand command);
}

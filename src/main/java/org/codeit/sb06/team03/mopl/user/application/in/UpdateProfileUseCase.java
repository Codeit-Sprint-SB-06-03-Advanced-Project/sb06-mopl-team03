package org.codeit.sb06.team03.mopl.user.application.in;

import org.codeit.sb06.team03.mopl.user.domain.Profile;

public interface UpdateProfileUseCase {

    Profile update(UpdateProfileCommand command);
}

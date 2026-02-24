package org.codeit.sb06.team03.mopl.user.application.in;

import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.UUID;

public interface GetProfileUseCase {
    Profile get(UUID accountId);
}

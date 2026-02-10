package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.Optional;
import java.util.UUID;

public interface LoadProfilePort {

    Optional<Profile> load(UUID accountId);
}

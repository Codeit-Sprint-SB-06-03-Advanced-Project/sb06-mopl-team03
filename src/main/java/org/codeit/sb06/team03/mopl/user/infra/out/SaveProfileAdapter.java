package org.codeit.sb06.team03.mopl.user.infra.out;

import org.codeit.sb06.team03.mopl.user.application.out.SaveProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.springframework.stereotype.Component;

@Component
public class SaveProfileAdapter implements SaveProfilePort {

    private final ProfileRepository repository;

    public SaveProfileAdapter(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profile save(Profile profile) {
        return repository.save(profile);
    }
}

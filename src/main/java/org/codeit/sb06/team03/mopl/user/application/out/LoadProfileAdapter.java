package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.infra.out.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoadProfileAdapter implements LoadProfilePort {

    private final ProfileRepository repository;

    public LoadProfileAdapter(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Profile> load(UUID accountId) {
        return repository.findById(accountId);
    }
}

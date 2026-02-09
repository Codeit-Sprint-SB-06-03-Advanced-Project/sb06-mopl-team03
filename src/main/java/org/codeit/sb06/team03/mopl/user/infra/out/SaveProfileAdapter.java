package org.codeit.sb06.team03.mopl.user.infra.out;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.user.application.out.SaveProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveProfileAdapter implements SaveProfilePort {

    private final ProfileMapper mapper;
    private final ProfileRepository repository;

    public SaveProfileAdapter(ProfileMapper mapper, ProfileRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Profile save(Profile profile) {
        JpaProfile jpaProfile = mapper.toJpa(profile);
        repository.save(jpaProfile);
        return profile;
    }
}

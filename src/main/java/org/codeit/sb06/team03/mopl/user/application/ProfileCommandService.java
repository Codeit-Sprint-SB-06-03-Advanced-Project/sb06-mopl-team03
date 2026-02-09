package org.codeit.sb06.team03.mopl.user.application;

import org.codeit.sb06.team03.mopl.user.application.in.CreateProfileCommand;
import org.codeit.sb06.team03.mopl.user.application.in.CreateProfileUseCase;
import org.codeit.sb06.team03.mopl.user.application.out.PublishProfileEventPort;
import org.codeit.sb06.team03.mopl.user.application.out.SaveProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.ProfileService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCommandService implements CreateProfileUseCase {

    private final ProfileService profileService;
    private final SaveProfilePort saveProfilePort;
    private final PublishProfileEventPort publishProfileEventPort;

    public ProfileCommandService(
            ProfileService profileService,
            SaveProfilePort saveProfilePort,
            PublishProfileEventPort publishProfileEventPort
    ) {
        this.profileService = profileService;
        this.saveProfilePort = saveProfilePort;
        this.publishProfileEventPort = publishProfileEventPort;
    }

    @Override
    public void create(CreateProfileCommand command) {
        final UUID accountId = command.accountId();
        final String name = command.name();

        Profile profile = profileService.create(accountId, name);
        Profile saved = saveProfilePort.save(profile);
        saved.events().forEach(publishProfileEventPort::publish);
        saved.clearEvents();
    }
}

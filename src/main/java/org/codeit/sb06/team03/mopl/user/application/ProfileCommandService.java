package org.codeit.sb06.team03.mopl.user.application;

import org.codeit.sb06.team03.mopl.user.application.in.CreateProfileCommand;
import org.codeit.sb06.team03.mopl.user.application.in.CreateProfileUseCase;
import org.codeit.sb06.team03.mopl.user.application.in.UpdateProfileCommand;
import org.codeit.sb06.team03.mopl.user.application.in.UpdateProfileUseCase;
import org.codeit.sb06.team03.mopl.user.application.out.LoadProfilePort;
import org.codeit.sb06.team03.mopl.user.application.out.SaveProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.ProfileService;
import org.codeit.sb06.team03.mopl.user.domain.exception.ProfileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
public class ProfileCommandService implements CreateProfileUseCase, UpdateProfileUseCase {

    private final ProfileService profileService;
    private final SaveProfilePort saveProfilePort;
    private final LoadProfilePort loadProfilePort;

    public ProfileCommandService(
            ProfileService profileService,
            SaveProfilePort saveProfilePort,
            LoadProfilePort loadProfilePort
    ) {
        this.profileService = profileService;
        this.saveProfilePort = saveProfilePort;
        this.loadProfilePort = loadProfilePort;
    }

    @Override
    public Profile create(CreateProfileCommand command) {
        final UUID accountId = command.accountId();
        final String name = command.name();

        return profileService.create(accountId, name);
    }

    @Override
    public Profile update(UpdateProfileCommand command) {
        final UUID accountId = command.accountId();
        final String name = command.name();
        final MultipartFile image = command.image();

        Profile profile = loadProfilePort.load(accountId)
                .orElseThrow(() -> new ProfileNotFoundException(accountId));
        Profile updated = profileService.update(profile, name, image);
        return saveProfilePort.save(updated);
    }
}

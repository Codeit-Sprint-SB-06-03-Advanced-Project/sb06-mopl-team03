package org.codeit.sb06.team03.mopl.user.infra;

import org.codeit.sb06.team03.mopl.user.application.in.UpdateProfileCommand;
import org.codeit.sb06.team03.mopl.user.infra.in.UserUpdateRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class ProfileMapper {

    public UpdateProfileCommand toCommand(String userId, UserUpdateRequest request, @Nullable MultipartFile image) {
        final UUID accountId = UUID.fromString(userId);
        final String name = request.name();
        return new UpdateProfileCommand(accountId, name, image);
    }
}

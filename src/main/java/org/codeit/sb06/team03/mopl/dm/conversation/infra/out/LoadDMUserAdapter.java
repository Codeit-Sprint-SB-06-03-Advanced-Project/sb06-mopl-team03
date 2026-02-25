package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.user.application.in.GetProfileUseCase;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadDMUserAdapter implements LoadDMUserPort {

    private final GetProfileUseCase getProfileUseCase;

    @Override
    public DMUser findByUserId(UUID userId) {
        Profile profile = getProfileUseCase.getDMUserProfile(userId).orElseThrow();
        return new DMUser(userId, profile.getName(), profile.getTimeoutImage().getPresignedUrl() != null ? profile.getTimeoutImage().getPresignedUrl() : null);
    }
}

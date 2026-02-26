package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.user.application.in.GetProfileUseCase;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LoadDMUserAdapter implements LoadDMUserPort {

    private final GetProfileUseCase getProfileUseCase;

    @Override
    @Transactional(readOnly = true)
    public DMUser findByUserId(UUID userId) {
        Profile profile = getProfileUseCase.getDMUserProfile(userId).orElseThrow();
        TimeoutImage img = profile.getTimeoutImage();
        return new DMUser(userId, profile.getName(), img != null ? img.getPresignedUrl() : null);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UUID, DMUser> findByUserIds(Set<UUID> userIds) {
        return userIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        this::findByUserId
                ));
    }
}

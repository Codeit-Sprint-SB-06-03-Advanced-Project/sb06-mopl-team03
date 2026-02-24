package org.codeit.sb06.team03.mopl.follow.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.follow.domain.event.FollowEvent;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.user.application.out.LoadProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.exception.ProfileNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class FollowEventListener {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final LoadProfilePort loadProfilePort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFollowedEvent(FollowEvent.FollowedEvent event) {

        Profile profile = loadProfilePort.load(event.getFolloweeId())
                        .orElseThrow(() -> new ProfileNotFoundException(event.getFolloweeId()));

        createNotificationUseCase.create(
                event.getFolloweeId(),
                "%s님이 팔로우했어요.".formatted(profile.getName()),
                null,
                NotificationLevel.INFO
        );
    }

}

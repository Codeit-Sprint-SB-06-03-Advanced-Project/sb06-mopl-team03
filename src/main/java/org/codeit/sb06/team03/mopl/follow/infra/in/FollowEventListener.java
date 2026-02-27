package org.codeit.sb06.team03.mopl.follow.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.follow.domain.event.FollowEvent;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.codeit.sb06.team03.mopl.sse.application.SseUseCase;
import org.codeit.sb06.team03.mopl.user.application.in.GetProfileUseCase;
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
    private final SseUseCase sseUseCase;
    private final GetProfileUseCase getProfileUseCase;

    private static final String EVENT_NAME = "notifications";

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFollowedEvent(FollowEvent.FollowedEvent event) {

        Profile profile = getProfileUseCase.load(event.getFolloweeId())
                        .orElseThrow(() -> new ProfileNotFoundException(event.getFolloweeId()));

        NotificationDto notificationDto = createNotificationUseCase.create(
                event.getFolloweeId(),
                "%s님이 팔로우했어요.".formatted(profile.getName()),
                null,
                NotificationLevel.INFO
        );
        sseUseCase.send(notificationDto, EVENT_NAME, event.getFolloweeId());
    }

}

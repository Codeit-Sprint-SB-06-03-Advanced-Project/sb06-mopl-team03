package org.codeit.sb06.team03.mopl.playlist.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.follow.application.out.LoadFolloweePort;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.codeit.sb06.team03.mopl.follow.domain.exception.FolloweeNotFoundException;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.SaveCurationPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.SaveSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.event.PlaylistEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PlaylistEventListener {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final LoadSubscriptionPort loadSubscriptionPort;
    private final SaveCurationPort saveCurationPort;
    private final SaveSubscriptionPort saveSubscriptionPort;
    private final LoadFolloweePort loadFolloweePort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePlaylistCreatedEvent(PlaylistEvent.PlaylistCreatedEvent event) {

        Followee followee = loadFolloweePort.findById(event.getOwnerId())
                .orElseThrow(() -> new FolloweeNotFoundException(event.getOwnerId()));
        List<UUID> followerIds = followee.getFollowers().stream()
                .map(follower -> follower.getId().getFollowerId())
                .toList();

        final String notificationTitle = "%s 님이 새 플레이리스트 '%s'를 생성했어요."
                .formatted(event.getOwnerName(), event.getPlaylistTitle());
        createNotificationUseCase.createAll(
                followerIds,
                notificationTitle,
                null,
                NotificationLevel.INFO
        );
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSubscriptionCreatedEvent(PlaylistEvent.SubscriptionCreatedEvent event) {
        final String subscriberName = event.getSubscriberName();
        final String playlistTitle = event.getPlaylistTitle();
        createNotificationUseCase.create(
                event.getOwnerId(),
                "%s 님이 내 플레이리스트 %s 을(를) 구독했어요.".formatted(subscriberName, playlistTitle),
                null,
                NotificationLevel.INFO
        );
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void CurationAddedEvent(PlaylistEvent.CurationAddedEvent event) {

        List<UUID> subscriberIds = loadSubscriptionPort.findSubscriberIdsByPlaylistId(event.getPlaylistId());

        final String notificationTitle = "%s 플레이리스트에 컨텐츠가 추가되었어요."
                .formatted(event.getPlaylistTitle());
        createNotificationUseCase.createAll(
                        subscriberIds,
                        notificationTitle,
                        null, // TODO 컨텐츠명
                        NotificationLevel.INFO
        );
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePlaylistDeletedEvent(PlaylistEvent.PlaylistDeletedEvent event) {
        saveCurationPort.deleteAllByPlaylistId(event.getPlaylistId());
        saveSubscriptionPort.deleteAllByPlaylistId(event.getPlaylistId());
    }

}

package org.codeit.sb06.team03.mopl.playlist.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.application.out.SaveCurationPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.SaveSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.event.PlaylistEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PlaylistEventListener {

    private final SaveCurationPort saveCurationPort;
    private final SaveSubscriptionPort saveSubscriptionPort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePlaylistCreatedEvent(PlaylistEvent.PlaylistCreatedEvent event) {
        // TODO 구독자에게 플레이리스트 생성 알림 전송
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSubscriptionCreatedEvent(PlaylistEvent.SubscriptionCreatedEvent event) {
        // TODO 플레이리스트 주인에게 알림 발송
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void CurationAddedEvent(PlaylistEvent.CurationAddedEvent event) {
        // TODO 구독자에게 알림 발송
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePlaylistDeletedEvent(PlaylistEvent.PlaylistDeletedEvent event) {
        saveCurationPort.deleteAllByPlaylistId(event.getPlaylistId());
        saveSubscriptionPort.deleteAllByPlaylistId(event.getPlaylistId());
    }

}

package org.codeit.sb06.team03.mopl.playlist.infra.out;

import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.QSubscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends QuerydslJpaRepository<Subscription, SubscriptionId> {

    boolean existsById(SubscriptionId id);

    Optional<Subscription> findById(SubscriptionId id);

    void deleteById(SubscriptionId id);

    default void deleteAllByPlaylistId(UUID playlistId) {
        QSubscription subscription = QSubscription.subscription;
        delete(subscription)
                .where(subscription.id.playlistId.eq(playlistId))
                .execute();
    }

    default void deleteAllBySubscriberId(UUID subscriberId) {
        QSubscription subscription = QSubscription.subscription;
        delete(subscription)
                .where(subscription.id.subscriberId.eq(subscriberId))
                .execute();
    }


}

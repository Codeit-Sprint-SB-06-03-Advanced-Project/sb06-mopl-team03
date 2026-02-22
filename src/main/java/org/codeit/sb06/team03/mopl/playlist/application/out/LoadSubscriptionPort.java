package org.codeit.sb06.team03.mopl.playlist.application.out;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;

import java.util.Optional;

public interface LoadSubscriptionPort {

    boolean existsById(SubscriptionId id);

    Optional<Subscription> findById(SubscriptionId id);

}

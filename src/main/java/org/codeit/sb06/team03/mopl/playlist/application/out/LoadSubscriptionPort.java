package org.codeit.sb06.team03.mopl.playlist.application.out;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;

import java.util.*;

public interface LoadSubscriptionPort {

    boolean existsById(SubscriptionId id);

    Map<UUID, Boolean> existsByIdIn(List<UUID> playlistIds, UUID userId);

    Optional<Subscription> findById(SubscriptionId id);

    List<UUID> findSubscriberIdsByPlaylistId(UUID playlistId);

}

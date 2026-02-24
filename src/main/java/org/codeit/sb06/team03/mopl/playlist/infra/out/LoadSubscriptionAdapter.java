package org.codeit.sb06.team03.mopl.playlist.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class LoadSubscriptionAdapter implements LoadSubscriptionPort {

    private final SubscriptionRepository repository;

    @Override
    public boolean existsById(SubscriptionId id) {
        return repository.existsById(id);
    }

    @Override
    public Map<UUID, Boolean> existsByIdIn(List<UUID> ids, UUID userId) {
        return repository.findAllSubscribedMap(ids, userId);
    }

    @Override
    public Optional<Subscription> findById(SubscriptionId id) {
        return repository.findById(id);
    }

    @Override
    public List<UUID> findSubscriberIdsByPlaylistId(UUID playlistId) {
        return repository.findSubscriberIdsByPlaylistId(playlistId);
    }
}

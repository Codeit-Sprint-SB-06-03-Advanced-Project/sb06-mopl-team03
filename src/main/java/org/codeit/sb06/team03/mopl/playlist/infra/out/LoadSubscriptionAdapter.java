package org.codeit.sb06.team03.mopl.playlist.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoadSubscriptionAdapter implements LoadSubscriptionPort {

    private final SubscriptionRepository repository;

    @Override
    public boolean existsById(SubscriptionId id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Subscription> findById(SubscriptionId id) {
        return repository.findById(id);
    }
}

package org.codeit.sb06.team03.mopl.follow.infra.out;

import org.codeit.sb06.team03.mopl.follow.application.out.LoadFolloweePort;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoadFolloweeAdapter implements LoadFolloweePort {

    private final JpaFollowRepository repository;

    public LoadFolloweeAdapter(JpaFollowRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Followee> findById(UUID followeeId) {
        return repository.findById(followeeId);
    }
}

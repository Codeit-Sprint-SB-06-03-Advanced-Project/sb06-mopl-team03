package org.codeit.sb06.team03.mopl.follow.infra.out;

import org.codeit.sb06.team03.mopl.follow.application.out.SaveFolloweePort;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.springframework.stereotype.Component;

@Component
public class SaveFolloweeAdapter implements SaveFolloweePort {

    private final JpaFollowRepository repository;

    public SaveFolloweeAdapter(JpaFollowRepository repository) {
        this.repository = repository;
    }

    @Override
    public Followee save(Followee followee) {
        return repository.save(followee);
    }
}

package org.codeit.sb06.team03.mopl.follow.application.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.follow.application.out.LoadFolloweePort;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FolloweeQueryService implements GetFolloweeUseCase {

    private final LoadFolloweePort loadFolloweePort;

    @Override
    public Optional<Followee> findById(UUID id) {
        return loadFolloweePort.findById(id);
    }
}

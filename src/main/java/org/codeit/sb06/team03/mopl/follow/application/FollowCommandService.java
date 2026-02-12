package org.codeit.sb06.team03.mopl.follow.application;

import org.codeit.sb06.team03.mopl.follow.application.in.*;
import org.codeit.sb06.team03.mopl.follow.application.out.LoadFolloweePort;
import org.codeit.sb06.team03.mopl.follow.application.out.SaveFolloweePort;
import org.codeit.sb06.team03.mopl.follow.domain.FollowService;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.codeit.sb06.team03.mopl.follow.domain.exception.FolloweeNotFoundException;
import org.codeit.sb06.team03.mopl.follow.infra.in.FollowDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FollowCommandService implements CreateFollowUseCase, ToggleFollowUseCase, GetFollowUseCase {

    private final FollowService service;
    private final SaveFolloweePort saveFolloweePort;
    private final LoadFolloweePort loadFolloweePort;

    public FollowCommandService(
            FollowService service,
            SaveFolloweePort saveFolloweePort,
            LoadFolloweePort loadFolloweePort
    ) {
        this.service = service;
        this.saveFolloweePort = saveFolloweePort;
        this.loadFolloweePort = loadFolloweePort;
    }

    @Override
    public Followee create(CreateFollowCommand command) {
        UUID id = command.accountId();
        Followee followee = service.create(id);
        return saveFolloweePort.save(followee);
    }

    @Override
    @Transactional(readOnly = true)
    public long count(String followeeId) {
        final UUID followeeUUID = UUID.fromString(followeeId);
        return loadFolloweePort.findById(followeeUUID)
                .map(Followee::getFolloweeCount)
                .orElse(0L);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean followedByMe(FollowQuery query) {
        UUID followeeId = query.followeeId();
        UUID followerId = query.followerId();
        Followee followee = loadFolloweePort.findById(followeeId)
                .orElseThrow(() -> new FolloweeNotFoundException(followeeId));
        return followee.isFollowedBy(followerId);
    }

    @Override
    public FollowDto follow(FollowCommand command) {
        UUID followeeId = command.followeeId();
        UUID followerId = command.followerId();

        Followee followee = loadFolloweePort.findById(followeeId)
                .orElseThrow(() -> new FolloweeNotFoundException(followeeId));
        Followee followed = service.follow(followee, followerId);
        Followee saved = saveFolloweePort.save(followed);
        return new FollowDto(saved.getId(), followeeId, followerId);
    }

    @Override
    public void unfollow(UnfollowCommand command) {
        UUID followerId = command.followerId();
        UUID unfollowId = command.unfollowId();

        Followee followee = loadFolloweePort.findById(unfollowId)
                .orElseThrow(() -> new FolloweeNotFoundException(unfollowId));
        Followee followed = service.unfollow(followee, followerId);
        saveFolloweePort.save(followed);
    }
}

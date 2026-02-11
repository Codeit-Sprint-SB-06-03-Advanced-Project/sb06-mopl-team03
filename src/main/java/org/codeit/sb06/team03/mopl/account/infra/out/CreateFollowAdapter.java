package org.codeit.sb06.team03.mopl.account.infra.out;

import org.codeit.sb06.team03.mopl.account.application.out.CreateFollowPort;
import org.codeit.sb06.team03.mopl.follow.application.in.CreateFollowCommand;
import org.codeit.sb06.team03.mopl.follow.application.in.CreateFollowUseCase;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;
import org.codeit.sb06.team03.mopl.follow.infra.FollowMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CreateFollowAdapter implements CreateFollowPort {

    private final CreateFollowUseCase createFollowUseCase;
    private final FollowMapper mapper;

    public CreateFollowAdapter(CreateFollowUseCase createFollowUseCase, FollowMapper mapper) {
        this.createFollowUseCase = createFollowUseCase;
        this.mapper = mapper;
    }

    @Override
    public CompletableFuture<Followee> create(UUID accountId) {
        return CompletableFuture.supplyAsync(() -> {
            CreateFollowCommand command = mapper.toCommand(accountId);
            return createFollowUseCase.create(command);
        });
    }
}

package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.follow.domain.Followee;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreateFollowPort {

    CompletableFuture<Followee> create(UUID accountId);
}

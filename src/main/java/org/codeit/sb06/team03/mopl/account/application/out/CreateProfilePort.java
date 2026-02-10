package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreateProfilePort {

    CompletableFuture<Profile> create(UUID accountId, String name);
}

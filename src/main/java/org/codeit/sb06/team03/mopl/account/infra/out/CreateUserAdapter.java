package org.codeit.sb06.team03.mopl.account.infra.out;

import org.codeit.sb06.team03.mopl.account.application.out.CreateUserPort;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CreateUserAdapter implements CreateUserPort {

    @Override
    public CompletableFuture<Void> create(UUID accountId, String name) {
        return CompletableFuture.supplyAsync(() -> null); // TODO: User In-Bound Port 구현시 추가해주세요.
    }
}

package org.codeit.sb06.team03.mopl.account.application.out;

import java.util.concurrent.CompletableFuture;

public interface CreateUserPort {

    CompletableFuture<Void> create(String name);
}

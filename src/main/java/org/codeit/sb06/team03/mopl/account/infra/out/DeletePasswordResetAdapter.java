package org.codeit.sb06.team03.mopl.account.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.out.DeletePasswordResetPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DeletePasswordResetAdapter implements DeletePasswordResetPort {

    private final PasswordResetRepository repository;

    @Override
    public void deleteByAccountId(UUID accountId) {
        repository.deleteById(accountId);
    }
}

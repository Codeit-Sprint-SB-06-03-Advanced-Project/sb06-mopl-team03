package org.codeit.sb06.team03.mopl.account.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.out.LoadPasswordResetPort;
import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadPasswordResetAdapter implements LoadPasswordResetPort {

    private final PasswordResetRepository repository;

    @Override
    public Optional<PasswordReset> findByAccountId(UUID accountId) {
        return repository.findById(accountId);
    }

}

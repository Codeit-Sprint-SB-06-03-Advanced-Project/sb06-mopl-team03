package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;

import java.util.Optional;
import java.util.UUID;

public interface LoadPasswordResetPort {

    Optional<PasswordReset> findByAccountId(UUID accountId);

}

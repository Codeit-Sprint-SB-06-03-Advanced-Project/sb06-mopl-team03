package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {

    boolean existsByEmailAddress(EmailAddress emailAddress);

    Optional<Account> findById(UUID accountId);
}

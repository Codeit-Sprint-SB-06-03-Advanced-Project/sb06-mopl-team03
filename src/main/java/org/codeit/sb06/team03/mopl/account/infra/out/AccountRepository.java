package org.codeit.sb06.team03.mopl.account.infra.out;

import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByEmailAddress(EmailAddress emailAddress);
}

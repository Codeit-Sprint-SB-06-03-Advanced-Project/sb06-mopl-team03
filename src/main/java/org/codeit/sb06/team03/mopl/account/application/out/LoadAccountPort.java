package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorRequestUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {

    boolean existsByEmailAddress(EmailAddress emailAddress);

    Optional<Account> findById(UUID accountId);

    List<UserDto> findAll(CursorRequestUserDto query);

    Long count(CursorRequestUserDto query);
}

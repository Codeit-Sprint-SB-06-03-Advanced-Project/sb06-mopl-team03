package org.codeit.sb06.team03.mopl.account.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorRequestUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadAccountAdapter implements LoadAccountPort {

    private final AccountRepository repository;

    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        return repository.existsByEmailAddress(emailAddress);
    }

    @Override
    public Optional<Account> findById(UUID accountId) {
        return repository.findById(accountId);
    }

    @Override
    public List<UserDto> findAll(CursorRequestUserDto query) {
        return repository.findAll(query);
    }

    @Override
    public Long count(CursorRequestUserDto query) {
        return repository.count(query);
    }
}

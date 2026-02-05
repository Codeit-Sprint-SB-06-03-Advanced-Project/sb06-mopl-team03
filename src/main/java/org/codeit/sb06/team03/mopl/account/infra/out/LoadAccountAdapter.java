package org.codeit.sb06.team03.mopl.account.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoadAccountAdapter implements LoadAccountPort {

    private final AccountRepository repository;

    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        return repository.existsByEmailAddress(emailAddress);
    }
}

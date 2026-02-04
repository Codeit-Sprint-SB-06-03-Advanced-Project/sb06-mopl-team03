package org.codeit.sb06.team03.mopl.account.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.out.SaveAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveAccountAdapter implements SaveAccountPort {

    private final AccountRepository repository;

    @Override
    public void save(Account account) {
        repository.save(account);
    }
}

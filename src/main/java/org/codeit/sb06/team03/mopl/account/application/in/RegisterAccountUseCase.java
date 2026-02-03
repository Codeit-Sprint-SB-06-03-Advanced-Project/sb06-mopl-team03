package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.account.domain.Account;

public interface RegisterAccountUseCase {

    Account register(RegisterAccountCommand command);
}

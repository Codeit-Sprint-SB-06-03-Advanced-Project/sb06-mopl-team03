package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.account.domain.vo.Email;

public interface LoadAccountPort {

    boolean existsByEmail(Email email);
}

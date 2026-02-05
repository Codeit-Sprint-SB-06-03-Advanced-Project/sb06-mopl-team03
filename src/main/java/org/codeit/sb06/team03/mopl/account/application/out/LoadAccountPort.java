package org.codeit.sb06.team03.mopl.account.application.out;

import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

public interface LoadAccountPort {

    boolean existsByEmailAddress(EmailAddress emailAddress);
}

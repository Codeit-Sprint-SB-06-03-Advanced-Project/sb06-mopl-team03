package org.codeit.sb06.team03.mopl.account.domain.policy;

import org.codeit.sb06.team03.mopl.account.domain.vo.Password;

public interface PasswordEncryptionPolicy {

    Password apply(String rawPassword);
}

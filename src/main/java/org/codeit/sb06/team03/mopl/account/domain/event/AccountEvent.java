package org.codeit.sb06.team03.mopl.account.domain.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class AccountEvent {

    public static final class AccountRegisteredEvent extends AccountEvent {
    }
}

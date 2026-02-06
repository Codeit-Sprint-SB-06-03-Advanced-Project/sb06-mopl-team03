package org.codeit.sb06.team03.mopl.account.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Role;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class AccountEvent {

    public static final class AccountRegisteredEvent extends AccountEvent {
    }

    @RequiredArgsConstructor
    public static final class RoleUpdatedEvent extends AccountEvent {
        private final Role role;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class AccountLockUpdatedEvent extends AccountEvent {
        private final UUID accountId;
        private final boolean locked;
    }
}

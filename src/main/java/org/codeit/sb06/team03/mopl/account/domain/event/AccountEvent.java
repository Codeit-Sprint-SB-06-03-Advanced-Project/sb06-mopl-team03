package org.codeit.sb06.team03.mopl.account.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class AccountEvent {

    public static final class AccountRegisteredEvent extends AccountEvent {
    }

    @RequiredArgsConstructor
    @Getter
    public static final class PasswordResetedEvent extends AccountEvent {
        private final String emailAddress;
        private final String rawTempPassword;
        private final String expiresAt;
    }

    @RequiredArgsConstructor
    public static final class RoleUpdatedEvent extends AccountEvent {
        private final Role role;
    }
}

package org.codeit.sb06.team03.mopl.account.domain.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Role;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class AccountEvent {

    public static final class AccountRegisteredEvent extends AccountEvent {
    }

    @RequiredArgsConstructor
    public static final class PasswordResetedEvent extends AccountEvent {
        private final EmailAddress emailAddress;
        private final String rawTempPassword;
        private final Instant expiresAt;
    }

    @RequiredArgsConstructor
    public static final class RoleUpdatedEvent extends AccountEvent {
        private final Role role;
    }
}

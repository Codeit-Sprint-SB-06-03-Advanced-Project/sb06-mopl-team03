package org.codeit.sb06.team03.mopl.email.infra.in;

import org.codeit.sb06.team03.mopl.account.domain.event.AccountEvent;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailCommand;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PasswordResetMapper {

    public SendEmailCommand toCommand(AccountEvent.PasswordResetedEvent event) {
        final String to = event.getEmailAddress();
        final String rawTempPassword = event.getRawTempPassword();
        final Instant expiresAt = Instant.parse(event.getExpiresAt());
        return new SendEmailCommand(to, rawTempPassword, expiresAt);
    }
}

package org.codeit.sb06.team03.mopl.email.application.in;

import java.time.Instant;

public record SendEmailCommand(
        String emailAddress,
        String rawTempPassword,
        Instant expireDate
){
}

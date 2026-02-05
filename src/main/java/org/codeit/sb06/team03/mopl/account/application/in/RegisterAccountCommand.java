package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

public record RegisterAccountCommand(String name, EmailAddress emailAddress, String rawPassword) {
}

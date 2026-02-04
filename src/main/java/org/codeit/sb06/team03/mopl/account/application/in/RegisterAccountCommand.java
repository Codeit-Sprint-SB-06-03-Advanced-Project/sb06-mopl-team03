package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.account.domain.vo.Email;

public record RegisterAccountCommand(String name, Email email, String rawPassword) {
}

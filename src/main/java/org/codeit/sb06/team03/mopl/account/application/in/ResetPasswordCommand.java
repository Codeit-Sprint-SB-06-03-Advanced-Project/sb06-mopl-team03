package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;

public record ResetPasswordCommand(EmailAddress emailAddress) {
}

package org.codeit.sb06.team03.mopl.account.application.in;

public record UpdatePasswordCommand(String accountId, String newPassword) {
}

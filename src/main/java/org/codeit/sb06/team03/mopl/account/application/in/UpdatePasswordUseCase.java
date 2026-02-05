package org.codeit.sb06.team03.mopl.account.application.in;

public interface UpdatePasswordUseCase {

    void updatePassword(String accountId, UpdatePasswordCommand command);

}

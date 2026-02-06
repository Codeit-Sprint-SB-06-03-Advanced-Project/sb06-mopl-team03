package org.codeit.sb06.team03.mopl.account.application.in;

public interface UpdateLockStatusUseCase {

    void updateLocked(String userId, UpdateLockStatusCommand command);
}

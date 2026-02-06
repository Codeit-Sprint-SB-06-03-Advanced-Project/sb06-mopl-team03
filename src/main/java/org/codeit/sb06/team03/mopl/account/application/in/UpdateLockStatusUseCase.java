package org.codeit.sb06.team03.mopl.account.application.in;

import java.util.UUID;

public interface UpdateLockStatusUseCase {

    void updateLocked(UUID userId, UpdateLockStatusCommand command);
}

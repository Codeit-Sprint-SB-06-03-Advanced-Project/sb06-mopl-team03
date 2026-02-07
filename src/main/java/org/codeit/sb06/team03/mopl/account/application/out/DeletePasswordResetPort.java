package org.codeit.sb06.team03.mopl.account.application.out;

import java.util.UUID;

public interface DeletePasswordResetPort {

    void deleteByAccountId(UUID accountId);

}

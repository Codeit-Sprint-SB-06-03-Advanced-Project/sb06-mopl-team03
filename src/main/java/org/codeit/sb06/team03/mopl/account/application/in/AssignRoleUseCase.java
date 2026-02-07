package org.codeit.sb06.team03.mopl.account.application.in;

import java.util.UUID;

public interface AssignRoleUseCase {

    void assignRole(UUID userId, AssignRoleCommand command);
}

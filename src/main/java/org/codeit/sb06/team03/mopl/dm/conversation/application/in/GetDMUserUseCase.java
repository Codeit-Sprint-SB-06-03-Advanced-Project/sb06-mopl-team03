package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface GetDMUserUseCase {
    DMUser findByUserId(UUID userId);
    Map<UUID, DMUser> findByUserIds(Set<UUID> userIds);
}

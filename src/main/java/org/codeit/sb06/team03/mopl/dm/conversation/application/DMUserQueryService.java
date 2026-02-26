package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetDMUserUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DMUserQueryService implements GetDMUserUseCase {

    private final LoadDMUserPort loadDMUserPort;

    @Override
    public DMUser findByUserId(UUID userId) {
        return loadDMUserPort.findByUserId(userId);
    }

    @Override
    public Map<UUID, DMUser> findByUserIds(Set<UUID> userIds) {
        return loadDMUserPort.findByUserIds(userIds);
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;

import java.util.UUID;

public interface LoadDMUserPort {
    DMUser findByUserId(UUID userId);
}

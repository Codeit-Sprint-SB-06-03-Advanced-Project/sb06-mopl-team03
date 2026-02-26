package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;

import java.util.UUID;

public interface LoadLiveDMUserPort {
    DMUser findByUserId(UUID userId);
}

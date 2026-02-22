package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;

import java.util.Optional;
import java.util.UUID;

public interface LoadConversationPort {

    Optional<ConversationDto> findByWith(UUID withUserId);
}

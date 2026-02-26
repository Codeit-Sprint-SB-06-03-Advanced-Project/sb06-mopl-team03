package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import java.util.List;
import java.util.UUID;

public interface LoadLiveMessageStatPort {

    boolean isActive(UUID conversationId, UUID accountId);

    List<UUID> findConversationIdsByAccountId(UUID accountId);
}

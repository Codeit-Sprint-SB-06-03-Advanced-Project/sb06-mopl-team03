package org.codeit.sb06.team03.mopl.dm.conversation.application.out;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;

public interface SaveConversationPort {
    Conversation save(Conversation conversation);
}

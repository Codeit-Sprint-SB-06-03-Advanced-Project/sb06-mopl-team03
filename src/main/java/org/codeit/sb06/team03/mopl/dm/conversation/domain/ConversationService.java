package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConversationService {

    public Conversation create(UUID userId, UUID withUserId) {
        return Conversation.create(userId, withUserId);
    }
}
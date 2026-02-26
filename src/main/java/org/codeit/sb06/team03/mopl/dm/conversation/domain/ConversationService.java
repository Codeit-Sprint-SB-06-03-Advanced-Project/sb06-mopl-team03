package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationCannotCreateWithSelfException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConversationService {

    public Conversation create(UUID userId, UUID withUserId) {
        if (userId.equals(withUserId)) {
            throw new ConversationCannotCreateWithSelfException(userId);
        }
        return Conversation.create(userId, withUserId);
    }
}
package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import java.time.Instant;
import java.util.UUID;

public interface MessagePassPort {
    void pass(UUID conversationId, UUID receiverId, UUID messageId, UUID senderId, String content, Instant createdAt);
}
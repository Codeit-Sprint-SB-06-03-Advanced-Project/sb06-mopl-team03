package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import java.util.UUID;

public interface MarkUnreadPort {
    void markAsUnread(UUID conversationId, UUID receiverId);
}

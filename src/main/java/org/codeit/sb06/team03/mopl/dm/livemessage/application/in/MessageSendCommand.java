package org.codeit.sb06.team03.mopl.dm.livemessage.application.in;

import java.util.UUID;

public record MessageSendCommand(
        UUID conversationId,
        UUID senderId,
        UUID receiverId,
        String content
) {
}

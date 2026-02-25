package org.codeit.sb06.team03.mopl.dm.livemessage.application.in;

import java.util.UUID;

public record MessageReceiveCommand(
        UUID messageId,
        UUID conversationId
) {
}

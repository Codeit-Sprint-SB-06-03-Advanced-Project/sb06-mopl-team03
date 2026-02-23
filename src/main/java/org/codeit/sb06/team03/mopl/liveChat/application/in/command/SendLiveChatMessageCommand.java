package org.codeit.sb06.team03.mopl.liveChat.application.in.command;

import java.util.UUID;

public record SendLiveChatMessageCommand(
        UUID accountId,
        String name,
        String profileImageUrl,
        String text,
        String destination
) {
}

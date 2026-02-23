package org.codeit.sb06.team03.mopl.liveChat.application.out.command;

import org.codeit.sb06.team03.mopl.common.UserSummary;

public record LiveChatMessage(
        UserSummary userSummary,
        String text,
        String destination
) {
}

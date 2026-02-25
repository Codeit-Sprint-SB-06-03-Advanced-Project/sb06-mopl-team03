package org.codeit.sb06.team03.mopl.liveChat.application.out.query;

import org.codeit.sb06.team03.mopl.common.UserSummary;

public record SendLiveChatMessageQuery(
        UserSummary userSummary,
        String text,
        String destination
) {
}

package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import org.codeit.sb06.team03.mopl.common.UserSummary;

public record LiveChatMessageResponse(
        UserSummary sender,
        String content
) {
}
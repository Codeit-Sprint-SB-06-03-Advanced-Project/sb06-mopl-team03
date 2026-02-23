package org.codeit.sb06.team03.mopl.liveChat.application.out;

import java.util.UUID;

public interface LiveChatWatchingSessionQueryPort {

    int countByLiveChatId(UUID liveChatId);
}

package org.codeit.sb06.team03.mopl.liveChat.application.out;

import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendLiveChatMessageQuery;
import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendPresenceMessageQuery;

public interface SendMessagePort {

    void broadcastPresenceMessage(SendPresenceMessageQuery sendPresenceMessageQuery);

    void broadcastLiveChatMessage(SendLiveChatMessageQuery sendLiveChatMessageQuery);
}

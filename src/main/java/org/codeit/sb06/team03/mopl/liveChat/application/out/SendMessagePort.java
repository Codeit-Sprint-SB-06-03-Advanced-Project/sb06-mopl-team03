package org.codeit.sb06.team03.mopl.liveChat.application.out;

import org.codeit.sb06.team03.mopl.liveChat.application.out.command.LiveChatMessage;
import org.codeit.sb06.team03.mopl.liveChat.application.out.command.PresenceMessage;

public interface SendMessagePort {

    void broadcastPresenceMessage(PresenceMessage presenceMessage);

    void broadcastLiveChatMessage(LiveChatMessage liveChatMessage);
}

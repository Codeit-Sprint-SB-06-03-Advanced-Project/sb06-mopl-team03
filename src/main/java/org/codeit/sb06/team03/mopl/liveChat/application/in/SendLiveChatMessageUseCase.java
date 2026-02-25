package org.codeit.sb06.team03.mopl.liveChat.application.in;

import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendLiveChatMessageCommand;

public interface SendLiveChatMessageUseCase {

    void sendLiveChatMessage(SendLiveChatMessageCommand sendLiveChatMessageCommand);
}

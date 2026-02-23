package org.codeit.sb06.team03.mopl.liveChat.application.in;

import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendPresenceMessageCommand;

import java.util.UUID;

public interface SendPresenceMessageUseCase {

    void sendPresenceMessage(
            UUID liveChatId,
            SendPresenceMessageCommand sendPresenceMessageCommand
    );
}
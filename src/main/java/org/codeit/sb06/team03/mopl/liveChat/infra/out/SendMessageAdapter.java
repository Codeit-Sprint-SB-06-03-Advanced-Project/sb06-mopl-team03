package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.UserSummary;
import org.codeit.sb06.team03.mopl.liveChat.application.out.SendMessagePort;
import org.codeit.sb06.team03.mopl.liveChat.application.out.command.LiveChatMessage;
import org.codeit.sb06.team03.mopl.liveChat.application.out.command.PresenceMessage;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessageAdapter implements SendMessagePort {

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void broadcastPresenceMessage(PresenceMessage presenceMessage) {
        UserSummary userSummary = presenceMessage.userSummary();
        ContentResult contentResult = presenceMessage.contentResult();

        SessionDetails sessionDetails = new SessionDetails(
                presenceMessage.watchingSessionId(),
                presenceMessage.watchingSessionCreatedAt(),
                userSummary,
                contentResult
        );

        LiveChatPresenceResponse response = new LiveChatPresenceResponse(
                presenceMessage.type(),
                sessionDetails,
                presenceMessage.count()
        );

        messagingTemplate.convertAndSend(presenceMessage.destination(), response);
    }

    @Override
    public void broadcastLiveChatMessage(LiveChatMessage liveChatMessage) {
        UserSummary userSummary = liveChatMessage.userSummary();
        String text = liveChatMessage.text();
        String destination = liveChatMessage.destination();

        LiveChatMessageResponse response = new LiveChatMessageResponse(userSummary, text);

        messagingTemplate.convertAndSend(destination, response);
    }
}

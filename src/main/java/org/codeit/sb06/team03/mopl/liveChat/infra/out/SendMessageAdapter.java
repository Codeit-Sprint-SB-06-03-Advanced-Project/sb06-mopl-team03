package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.UserSummary;
import org.codeit.sb06.team03.mopl.liveChat.application.out.SendMessagePort;
import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendLiveChatMessageQuery;
import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendPresenceMessageQuery;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessageAdapter implements SendMessagePort {

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void broadcastPresenceMessage(SendPresenceMessageQuery sendPresenceMessageQuery) {
        UserSummary userSummary = sendPresenceMessageQuery.userSummary();
        ContentResult contentResult = sendPresenceMessageQuery.contentResult();

        SessionDetails sessionDetails = new SessionDetails(
                sendPresenceMessageQuery.watchingSessionId(),
                sendPresenceMessageQuery.watchingSessionCreatedAt(),
                userSummary,
                contentResult
        );

        LiveChatPresenceResponse response = new LiveChatPresenceResponse(
                sendPresenceMessageQuery.type(),
                sessionDetails,
                sendPresenceMessageQuery.count()
        );

        messagingTemplate.convertAndSend(sendPresenceMessageQuery.destination(), response);
    }

    @Override
    public void broadcastLiveChatMessage(SendLiveChatMessageQuery sendLiveChatMessageQuery) {
        UserSummary userSummary = sendLiveChatMessageQuery.userSummary();
        String text = sendLiveChatMessageQuery.text();
        String destination = sendLiveChatMessageQuery.destination();

        LiveChatMessageResponse response = new LiveChatMessageResponse(userSummary, text);

        messagingTemplate.convertAndSend(destination, response);
    }
}

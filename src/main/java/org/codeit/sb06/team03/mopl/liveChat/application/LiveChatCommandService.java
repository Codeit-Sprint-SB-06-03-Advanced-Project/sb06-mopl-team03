package org.codeit.sb06.team03.mopl.liveChat.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.UserSummary;
import org.codeit.sb06.team03.mopl.liveChat.application.in.CreateLiveChatUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.DeleteLiveChatUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.SendLiveChatMessageUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.SendPresenceMessageUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendLiveChatMessageCommand;
import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendPresenceMessageCommand;
import org.codeit.sb06.team03.mopl.liveChat.application.out.*;
import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendLiveChatMessageQuery;
import org.codeit.sb06.team03.mopl.liveChat.application.out.query.SendPresenceMessageQuery;
import org.codeit.sb06.team03.mopl.liveChat.domain.LiveChat;
import org.codeit.sb06.team03.mopl.liveChat.domain.exception.LiveChatDuplicateException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiveChatCommandService implements
        SendPresenceMessageUseCase,  CreateLiveChatUseCase, DeleteLiveChatUseCase, SendLiveChatMessageUseCase {

    private final LoadLiveChatPort loadLiveChatPort;
    private final SendMessagePort sendMessagePort;
    private final SaveLiveChatPort saveLiveChatPort;
    private final DeleteLiveChatPort deleteLiveChatPort;
    private final LiveChatContentQueryPort liveChatContentQueryPort;
    private final LiveChatWatchingSessionQueryPort liveChatWatchingSessionQueryPort;

    @Override
    public void sendPresenceMessage(UUID liveChatId, SendPresenceMessageCommand command) {
        UUID contentId = liveChatId; // LiveChat과 Content는 같은 ID를 쓰고 있음

        ContentResult contentResult = liveChatContentQueryPort.findById(contentId);

        UserSummary userSummary = new UserSummary(command.accountId(), command.name(), command.profileImageUrl());

        int watcherCount = liveChatWatchingSessionQueryPort.countByLiveChatId(liveChatId);

        SendPresenceMessageQuery sendPresenceMessageQuery =
                new SendPresenceMessageQuery(
                        userSummary,
                        command.watchingSessionId(),
                        command.watchingSessionCreatedAt(),
                        watcherCount,
                        command.type(),
                        command.destination(),
                        contentResult
                );

        sendMessagePort.broadcastPresenceMessage(sendPresenceMessageQuery);
    }

    @Override
    public void sendLiveChatMessage(SendLiveChatMessageCommand command) {
        UserSummary userSummary = new UserSummary(command.accountId(), command.name(), command.profileImageUrl());
        String text = command.text();
        SendLiveChatMessageQuery sendLiveChatMessageQuery = new SendLiveChatMessageQuery(userSummary, text, command.destination());
        sendMessagePort.broadcastLiveChatMessage(sendLiveChatMessageQuery);
    }

    @Override
    @Transactional
    public void create(UUID contentId) {
        if (loadLiveChatPort.existsById(contentId)) {
            throw LiveChatDuplicateException.fromId(contentId);
        }

        LiveChat liveChat = LiveChat.create(contentId);
        saveLiveChatPort.save(liveChat);
    }

    @Override
    @Transactional
    public void delete(UUID contentId) {
        deleteLiveChatPort.deleteById(contentId);
    }
}
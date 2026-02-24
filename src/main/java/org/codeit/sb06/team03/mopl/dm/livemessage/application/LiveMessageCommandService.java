package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorResponseDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageReceiveUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadConversationParticipantsPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MessagePassPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.SaveLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LiveMessageCommandService implements MessageReceiveUseCase, MessageSendUseCase, GetDirectMessageUseCase {

    private final LiveMessageService liveMessageService;
    private final MessagePassPort messagePassPort;
    private final LoadLiveMessagePort loadLiveMessagePort;
    private final SaveLiveMessagePort saveLiveMessagePort;
    private final LoadConversationParticipantsPort loadConversationParticipantsPort;

    @Override
    public CursorResponseDirectMessageDto getDirectMessage(
            UUID userId,
            UUID conversationId,
            CursorRequestDirectMessageDto request
    ) {
        UUID withUserId = loadConversationParticipantsPort.findWithUserId(userId, conversationId);
        return loadLiveMessagePort.findAll(userId, withUserId, request);
    }
}

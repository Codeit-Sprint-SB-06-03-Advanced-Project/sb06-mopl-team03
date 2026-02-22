package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.MessageReadUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageJoinPort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageLeavePort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageStatsChangePort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.ConversationService;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConversationCommandService implements DMConnectUseCase, MessageReadUseCase {

    private final ConversationService conversationService;
    private final LiveMessageJoinPort liveMessageJoinPort;
    private final LiveMessageLeavePort liveMessageLeavePort;
    private final LiveMessageStatsChangePort liveMessageStatsChangePort;

    private final LoadConversationPort loadConversationPort;

    @Override
    @Transactional
    public ConversationDto connect(DMConnectCommand command) {
        final UUID withUserId = command.withUserId();
        return loadConversationPort.findByWith(withUserId)
                        .orElseThrow(() -> new ConversationNotFoundException(withUserId));
    }

    public Conversation create(DMConnectCommand command) {
        /* TODO : 아래 도메인 서비스 정의 및 구현 필요
        conversationService.create();
        conversationService.createSenderStat();
        conversationService.createReceiverStat();*/
        // TODO : 저장 후 성공 시, LiveMessage 생성 로직

        return null;
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.MessageReadUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageJoinPort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageLeavePort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LiveMessageStatsChangePort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConversationCommandService implements DMConnectUseCase, MessageReadUseCase {

    private final ConversationService conversationService;
    private final LiveMessageJoinPort liveMessageJoinPort;
    private final LiveMessageLeavePort liveMessageLeavePort;
    private final LiveMessageStatsChangePort liveMessageStatsChangePort;
}

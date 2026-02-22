package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.MessageReadUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BasicBffDMService implements BffDMService{

    private final DMMapper dmMapper;
    private final DMConnectUseCase dmConnectUseCase;
    private final MessageReadUseCase messageReadUseCase;

    @Override
    public CursorResponseConversationDto getConversations(CursorRequestConversationDto request) {
        return null;
    }

    @Override
    public ConversationDto postConversation(ConversationCreateRequest request) {
        return null;
    }

    @Override
    public void postReadDirectMessage(String conversationId, String directMessageId) {

    }

    @Override
    public ConversationDto getConversation(String conversationId) {
        return null;
    }

    @Override
    public CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request) {
        return null;
    }

    @Override
    public ConversationDto getConversationWith(String withUserId) {
        DMConnectCommand command = dmMapper.toCommand(withUserId);
        return dmConnectUseCase.connect(command);
    }
}

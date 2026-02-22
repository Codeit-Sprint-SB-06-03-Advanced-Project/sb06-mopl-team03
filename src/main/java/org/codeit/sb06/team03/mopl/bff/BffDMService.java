package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;

public interface BffDMService {

    CursorResponseConversationDto getConversations(CursorRequestConversationDto request);

    ConversationDto postConversation(ConversationCreateRequest request);

    void postReadDirectMessage(String conversationId, String directMessageId);

    ConversationDto getConversation(String conversationId);

    CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request);

    ConversationDto getConversationWith(String userId);
}

package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.request.*;
import org.codeit.sb06.team03.mopl.dm.livemessage.infra.in.request.*;

public interface BffDMService {

    CursorResponseConversationDto getConversations(CursorRequestConversationDto request);

    ConversationDto postConversation(ConversationCreateRequest request);

    void postReadDirectMessage(String conversationId, String directMessageId);

    ConversationDto getConversation(String conversationId);

    CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request);

    ConversationDto getConversationWith(String userId);

    void sendMessage(String conversationId, String senderId, MessageSendRequest request);
}

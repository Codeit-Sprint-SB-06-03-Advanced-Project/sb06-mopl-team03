package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffDMService implements BffDMService{

    private final DMMapper dmMapper;
    private final DMConnectUseCase dmConnectUseCase;
    private final DMCreateUseCase dmCreateUseCase;
    private final MessageReadUseCase messageReadUseCase;
    private final GetConversationUseCase getConversationUseCase;
    private final GetDirectMessageUseCase getDirectMessageUseCase;

    @Override
    public CursorResponseConversationDto getConversations(CursorRequestConversationDto request) {
        UUID userId = getCurrentUserId();
        return getConversationUseCase.findAll(userId, request);
    }

    @Override
    public ConversationDto postConversation(ConversationCreateRequest request) {
        UUID userId = getCurrentUserId();
        DMConnectCommand command = dmMapper.toCommand(request.withUserId());
        return dmCreateUseCase.create(userId, command);
    }

    @Override
    public void postReadDirectMessage(String conversationId, String directMessageId) {
        UUID userId = getCurrentUserId();
        UUID conversationUUID = UUID.fromString(conversationId);
        UUID directMessageUUID = UUID.fromString(directMessageId);
        messageReadUseCase.read(userId, conversationUUID, directMessageUUID);
    }

    @Override
    public ConversationDto getConversation(String conversationId) {
        UUID userId = getCurrentUserId();
        UUID conversationUUID = UUID.fromString(conversationId);
        return getConversationUseCase.findById(userId, conversationUUID);
    }

    @Override
    public CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request) {
        UUID userId = getCurrentUserId();
        UUID conversationUUID = UUID.fromString(conversationId);
        return getDirectMessageUseCase.getDirectMessage(userId, conversationUUID, request);
    }

    @Override
    public ConversationDto getConversationWith(String withUserId) {
        UUID userId = getCurrentUserId();
        DMConnectCommand command = dmMapper.toCommand(withUserId);
        return dmConnectUseCase.connect(userId, command);
    }

    private UUID getCurrentUserId() {
        MoplUserDetails user = (MoplUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}

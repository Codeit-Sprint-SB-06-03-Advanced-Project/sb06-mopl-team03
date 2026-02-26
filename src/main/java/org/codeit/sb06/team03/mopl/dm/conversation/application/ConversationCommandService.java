package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.SaveConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.ConversationService;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationAlreadyExistsException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.SaveLiveMessagePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ConversationCommandService implements CreateConversationUseCase, MessageReadUseCase, LiveMessageJoinUseCase, LiveMessageLeaveUseCase {

    private final ConversationService conversationService;
    private final LoadConversationPort loadConversationPort;
    private final SaveConversationPort saveConversationPort;
    private final SaveLiveMessagePort saveLiveMessagePort;

    @Override
    public Conversation create(UUID userId, CreateConversationCommand command) {
        UUID withUserId = command.withUserId();
        loadConversationPort.findByParticipants(userId, withUserId)
                .ifPresent(c -> { throw new ConversationAlreadyExistsException(withUserId); });

        Conversation conversation = conversationService.create(userId, withUserId);
        return saveConversationPort.save(conversation);
    }

    @Override
    public void read(MessageReadCommand command) {
        Conversation conversation = loadConversationPort.findById(command.conversationId())
                .orElseThrow(() -> new ConversationNotFoundException(command.conversationId()));

        conversationService.markAsRead(conversation, command.userId());
        saveConversationPort.save(conversation);

        saveLiveMessagePort.markAsRead(command.directMessageId());
    }

    @Override
    public void markAsUnread(UUID conversationId, UUID receiverId) {
        Conversation conversation = loadConversationPort.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId));

        conversationService.markAsUnread(conversation, receiverId);
        saveConversationPort.save(conversation);
    }

    @Override
    public void join(LiveMessageJoinCommand command) {
        Conversation conversation = loadConversationPort.findById(command.conversationId())
                .orElseThrow(() -> new ConversationNotFoundException(command.conversationId()));

        conversationService.joinLiveMessage(conversation, command.userId());
        saveConversationPort.save(conversation);
    }

    @Override
    public void leave(LiveMessageLeaveCommand command) {
        Conversation conversation = loadConversationPort.findById(command.conversationId())
                .orElseThrow(() -> new ConversationNotFoundException(command.conversationId()));

        conversationService.leaveLiveMessage(conversation, command.userId());
        saveConversationPort.save(conversation);
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMConnectUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.DMCreateUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.MessageReadUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.*;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.ConversationService;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationAlreadyExistsException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.Message;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DMUserDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DirectMessageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConversationCommandService implements DMConnectUseCase, MessageReadUseCase, DMCreateUseCase {

    private final ConversationService conversationService;
    private final LiveMessageJoinPort liveMessageJoinPort;
    private final LiveMessageLeavePort liveMessageLeavePort;
    private final LiveMessageStatsChangePort liveMessageStatsChangePort;
    private final LoadConversationPort loadConversationPort;
    private final SaveConversationPort saveConversationPort;
    private final LoadDMUserPort loadDMUserPort;

    @Override
    @Transactional
    public ConversationDto connect(UUID userId, DMConnectCommand command) {
        final UUID withUserId = command.withUserId();
        Conversation conversation = loadConversationPort.findByWith(userId, withUserId)
                .orElseThrow(() -> new ConversationNotFoundException(withUserId));

        return createConversationDto(conversation, withUserId, userId);
    }

    @Override
    @Transactional
    public ConversationDto create(UUID userId, DMConnectCommand command) {
        final UUID withUserId = command.withUserId();
        if (loadConversationPort.findByWith(userId, withUserId).isPresent()) {
            throw new ConversationAlreadyExistsException(withUserId);
        }
        Conversation newConversation = conversationService.create(userId, withUserId);
        saveConversationPort.save(newConversation);

        return createConversationDto(newConversation, withUserId, userId);
    }

    private ConversationDto createConversationDto(Conversation conversation, UUID withUserId, UUID userId) {
        final String conversationId = conversation.getId().toString();
        final Message message = conversation.getLastestMessage();
        final DMUser dmUser = loadDMUserPort.findByUserId(withUserId);
        final LiveMessageStat stat = conversation.getLiveMessageStats().get(userId);
        final boolean hasUnread = stat != null && stat.isHasUnread();

        return new ConversationDto(
                conversationId,
                createDMUserDto(dmUser),
                message != null ? createDirectMessageDto(conversationId, message) : null,
                hasUnread
        );
    }

    private DirectMessageDto createDirectMessageDto(String conversationId, Message message) {
        return new DirectMessageDto(
                message.messageId().toString(),
                conversationId,
                message.createdAt().toString(),
                createDMUserDto(message.sender()),
                createDMUserDto(message.receiver()),
                message.content()
        );
    }

    private DMUserDto createDMUserDto(DMUser dmUser) {
        return new DMUserDto(
                dmUser.userId().toString(),
                dmUser.name(),
                dmUser.profileImageUrl()
        );
    }

    @Override
    @Transactional
    public void read(UUID userId, UUID conversationId, UUID dmId) {
        Conversation conversation = loadConversationPort.findEntityById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId));
        conversation.markAsRead(userId);
        saveConversationPort.save(conversation);
    }
}

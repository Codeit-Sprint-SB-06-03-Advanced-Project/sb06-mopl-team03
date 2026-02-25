package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.request.ConversationCreateRequest;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendCommand;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.infra.in.request.MessageSendRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffDMService implements BffDMService {

    private final CreateConversationUseCase createConversationUseCase;
    private final GetConversationUseCase getConversationUseCase;
    private final MessageReadUseCase messageReadUseCase;
    private final MessageSendUseCase messageSendUseCase;
    private final GetDirectMessageUseCase getDirectMessageUseCase;
    private final LoadDMUserPort loadDMUserPort;
    private final LoadLiveMessagePort loadLiveMessagePort;
    private final DMMapper dmMapper;

    @Override
    public CursorResponseConversationDto getConversations(CursorRequestConversationDto request) {
        UUID userId = getCurrentUserId();
        int limit = request.limit();

        List<Conversation> items = getConversationUseCase.findAll(
                userId, request.cursor(), request.idAfter(), limit,
                request.sortDirection(), request.sortBy());

        boolean hasNext = items.size() > limit;
        List<Conversation> page = hasNext ? items.subList(0, limit) : items;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            Conversation last = page.get(page.size() - 1);
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        long totalCount = getConversationUseCase.countAll(userId);

        List<ConversationDto> data = page.stream()
                .map(conv -> toConversationDto(conv, userId))
                .toList();

        return new CursorResponseConversationDto(
                data, nextCursor, nextIdAfter, hasNext, totalCount,
                request.sortBy(), SortOrder.parse(request.sortDirection()));
    }

    @Override
    public ConversationDto postConversation(ConversationCreateRequest request) {
        UUID userId = getCurrentUserId();
        CreateConversationCommand command = dmMapper.toCommand(request.withUserId());
        Conversation conversation = createConversationUseCase.create(userId, command);
        return toConversationDto(conversation, userId);
    }

    @Override
    public void postReadDirectMessage(String conversationId, String directMessageId) {
        UUID userId = getCurrentUserId();
        messageReadUseCase.read(new MessageReadCommand(
                UUID.fromString(conversationId),
                UUID.fromString(directMessageId),
                userId
        ));
    }

    @Override
    public ConversationDto getConversation(String conversationId) {
        UUID userId = getCurrentUserId();
        Conversation conversation = getConversationUseCase.findById(userId, UUID.fromString(conversationId));
        return toConversationDto(conversation, userId);
    }

    @Override
    public CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request) {
        UUID convId = UUID.fromString(conversationId);
        int limit = request.limit();

        List<LiveMessage> items = getDirectMessageUseCase.findAll(
                convId, request.cursor(), request.idAfter(), limit,
                request.sortDirection(), request.sortBy());

        boolean hasNext = items.size() > limit;
        List<LiveMessage> page = hasNext ? items.subList(0, limit) : items;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            LiveMessage last = page.get(page.size() - 1);
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        long totalCount = getDirectMessageUseCase.countAll(convId);

        List<DirectMessageDto> data = page.stream()
                .map(this::toDirectMessageDto)
                .toList();

        return new CursorResponseDirectMessageDto(
                data, nextCursor, nextIdAfter, hasNext, totalCount,
                request.sortBy(), SortOrder.parse(request.sortDirection()));
    }

    @Override
    public ConversationDto getConversationWith(String withUserId) {
        UUID userId = getCurrentUserId();
        Conversation conversation = getConversationUseCase.findByWith(userId, UUID.fromString(withUserId));
        return toConversationDto(conversation, userId);
    }

    @Override
    public void sendMessage(String conversationId, String senderId, MessageSendRequest request) {
        UUID convId = UUID.fromString(conversationId);
        UUID senderUuid = UUID.fromString(senderId);

        Conversation conversation = getConversationUseCase.findById(senderUuid, convId);
        UUID receiverId = conversation.getOtherParticipant(senderUuid);

        messageSendUseCase.send(new MessageSendCommand(convId, senderUuid, receiverId, request.content()));
    }

    private ConversationDto toConversationDto(Conversation conv, UUID userId) {
        UUID withUserId = conv.getOtherParticipant(userId);
        DMUser with = loadDMUserPort.findByUserId(withUserId);

        LiveMessageStat stat = conv.getLiveMessageStats().get(userId);
        boolean hasUnread = stat != null && stat.isHasUnread();

        DirectMessageDto lastestMessage = loadLiveMessagePort
                .findLatestByConversationId(conv.getId())
                .map(this::toDirectMessageDto)
                .orElse(null);

        return new ConversationDto(
                conv.getId().toString(),
                DMUserDto.from(with),
                lastestMessage,
                hasUnread
        );
    }

    private DirectMessageDto toDirectMessageDto(LiveMessage msg) {
        DMUser sender = loadDMUserPort.findByUserId(msg.getSenderId());
        DMUser receiver = loadDMUserPort.findByUserId(msg.getReceiverId());

        return new DirectMessageDto(
                msg.getId().toString(),
                msg.getConversationId().toString(),
                msg.getCreatedAt().toString(),
                DMUserDto.from(sender),
                DMUserDto.from(receiver),
                msg.getContent()
        );
    }

    private UUID getCurrentUserId() {
        MoplUserDetails user = (MoplUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getId();
    }
}

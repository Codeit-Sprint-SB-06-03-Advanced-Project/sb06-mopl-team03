package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.request.ConversationCreateRequest;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendCommand;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.infra.in.request.MessageSendRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class BasicBffDMService implements BffDMService {

    private final CreateConversationUseCase createConversationUseCase;
    private final GetConversationUseCase getConversationUseCase;
    private final MessageReadUseCase messageReadUseCase;
    private final MessageSendUseCase messageSendUseCase;
    private final GetDirectMessageUseCase getDirectMessageUseCase;
    private final GetDMUserUseCase getDMUserUseCase;
    private final DMMapper dmMapper;

    @Override
    public CursorResponseConversationDto getConversations(CursorRequestConversationDto request) {
        UUID userId = getCurrentUserId();
        int limit = request.limit();

        List<Conversation> items = getConversationUseCase.findAll(
                userId,
                request.cursor(),
                request.idAfter(),
                limit,
                request.sortDirection(),
                request.sortBy()
        );

        boolean hasNext = items.size() > limit;
        List<Conversation> page = hasNext ? items.subList(0, limit) : items;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            Conversation last = page.getLast();
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        long totalCount = getConversationUseCase.countAll(userId);

        Set<UUID> userIds = page.stream()
                .map(conv -> conv.getOtherParticipant(userId))
                .collect(Collectors.toSet());

        Set<UUID> convIds = page.stream()
                .map(Conversation::getId)
                .collect(Collectors.toSet());
        Map<UUID, LiveMessage> latestMessages = getDirectMessageUseCase
                .findLatestByConversationIds(convIds);

        latestMessages.values().forEach(msg -> {
            userIds.add(msg.getSenderId());
            userIds.add(msg.getReceiverId());
        });

        Map<UUID, DMUser> userMap = getDMUserUseCase.findByUserIds(userIds);

        List<ConversationDto> data = page.stream()
                .map(conv -> toCursorConversationDto(conv, userId, userMap, latestMessages))
                .toList();

        return new CursorResponseConversationDto(
                data,
                nextCursor,
                nextIdAfter,
                hasNext,
                totalCount,
                request.sortBy(),
                SortOrder.parse(request.sortDirection())
        );
    }

    @Override
    public ConversationDto postConversation(ConversationCreateRequest request) {
        UUID userId = getCurrentUserId();
        CreateConversationCommand command = dmMapper.toCommand(request.withUserId());
        Conversation conversation = createConversationUseCase.create(userId, command);
        return toConversationDto(conversation, userId, Optional.empty());
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
        UUID convId = UUID.fromString(conversationId);
        Conversation conversation = getConversationUseCase.findById(userId, convId);
        Optional<LiveMessage> liveMessage = getDirectMessageUseCase.findLatestByConversationId(convId);
        return toConversationDto(conversation, userId, liveMessage);
    }

    @Override
    public CursorResponseDirectMessageDto getDirectMessages(String conversationId, CursorRequestDirectMessageDto request) {
        UUID convId = UUID.fromString(conversationId);
        int limit = request.limit();

        List<LiveMessage> items = getDirectMessageUseCase.findAll(
                convId,
                request.cursor(),
                request.idAfter(),
                limit,
                request.sortDirection(),
                request.sortBy()
        );

        boolean hasNext = items.size() > limit;
        List<LiveMessage> page = hasNext ? items.subList(0, limit) : items;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            LiveMessage last = page.getLast();
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        long totalCount = getDirectMessageUseCase.countAll(convId);

        Set<UUID> userIds = page.stream()
                .flatMap(msg -> Stream.of(msg.getSenderId(), msg.getReceiverId()))
                .collect(Collectors.toSet());
        Map<UUID, DMUser> userMap = getDMUserUseCase.findByUserIds(userIds);

        List<DirectMessageDto> data = page.stream()
                .map(msg -> toDirectMessageDto(msg, userMap))
                .toList();

        return new CursorResponseDirectMessageDto(
                data,
                nextCursor,
                nextIdAfter,
                hasNext,
                totalCount,
                request.sortBy(),
                SortOrder.parse(request.sortDirection())
        );
    }

    @Override
    public ConversationDto getConversationWith(String withUserId) {
        UUID userId = getCurrentUserId();
        Conversation conversation = getConversationUseCase.findByWith(userId, UUID.fromString(withUserId));
        return toConversationDto(conversation, userId, Optional.empty());
    }

    @Override
    public void sendMessage(String conversationId, String senderId, MessageSendRequest request) {
        UUID convId = UUID.fromString(conversationId);
        UUID senderUuid = UUID.fromString(senderId);

        Conversation conversation = getConversationUseCase.findById(senderUuid, convId);
        UUID receiverId = conversation.getOtherParticipant(senderUuid);

        messageSendUseCase.send(new MessageSendCommand(convId, senderUuid, receiverId, request.content()));
    }

    private ConversationDto toConversationDto(Conversation conversation, UUID userId, Optional<LiveMessage> liveMessage) {
        UUID withUserId = conversation.getOtherParticipant(userId);
        DMUser with = getDMUserUseCase.findByUserId(withUserId);
        DirectMessageDto latestMessage = liveMessage
                .map(msg -> {
                    DMUser me = getDMUserUseCase.findByUserId(userId);
                    return toDirectMessageDto(msg, Map.of(withUserId, with, userId, me));
                })
                .orElse(null);

        return new ConversationDto(
                conversation.getId().toString(),
                DMUserDto.from(with),
                latestMessage,
                false
        );
    }

    private ConversationDto toCursorConversationDto(
            Conversation conversation,
            UUID userId,
            Map<UUID, DMUser> userMap,
            Map<UUID, LiveMessage> latestMessages
    ) {
        UUID withUserId = conversation.getOtherParticipant(userId);
        DMUser with = userMap.get(withUserId);

        LiveMessageStat stat = conversation.getLiveMessageStats().get(userId);
        boolean hasUnread = stat != null && stat.isHasUnread();

        LiveMessage latestMsg = latestMessages.get(conversation.getId());
        DirectMessageDto latestMessage = latestMsg != null
                ? toDirectMessageDto(latestMsg, userMap)
                : null;

        return new ConversationDto(
                conversation.getId().toString(),
                DMUserDto.from(with),
                latestMessage,
                hasUnread
        );
    }

    private DirectMessageDto toDirectMessageDto(LiveMessage msg, Map<UUID, DMUser> userMap) {
        DMUser sender = userMap.get(msg.getSenderId());
        DMUser receiver = userMap.get(msg.getReceiverId());
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

package org.codeit.sb06.team03.mopl.dm.conversation.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetConversationUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.Message;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConversationQueryService implements GetConversationUseCase {

    private final LoadConversationPort loadConversationPort;
    private final LoadDMUserPort loadDMUserPort;

    @Override
    public CursorResponseConversationDto findAll(UUID userId, CursorRequestConversationDto request) {
        List<Conversation> items = loadConversationPort.findAll(userId, request);
        int limit = request.limit();

        boolean hasNext = items.size() > limit;
        List<Conversation> page = hasNext ? items.subList(0, limit) : items;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            Conversation last = page.get(page.size() - 1);
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        Long totalCount = loadConversationPort.count(userId);

        List<ConversationDto> data = page.stream()
                .map(conv -> toDto(conv, userId))
                .toList();

        return new CursorResponseConversationDto(
                data, nextCursor, nextIdAfter, hasNext, totalCount,
                request.sortBy(), SortOrder.valueOf(request.sortDirection())
        );
    }

    @Override
    public ConversationDto findById(UUID userId, UUID conversationId) {
        return loadConversationPort.findById(userId, conversationId)
                .map(conv -> toDto(conv, userId))
                .orElseThrow(() -> new ConversationNotFoundException(conversationId));
    }

    private ConversationDto toDto(Conversation conv, UUID userId) {
        UUID withUserId = conv.getLiveMessageStats().keySet().stream()
                .filter(id -> !id.equals(userId))
                .findFirst()
                .orElseThrow();
        DMUser with = loadDMUserPort.findByUserId(withUserId);

        Message msg = conv.getLastestMessage();
        DirectMessageDto lastMsgDto = null;
        if (msg != null) {
            lastMsgDto = new DirectMessageDto(
                    msg.messageId().toString(), conv.getId().toString(), msg.createdAt().toString(),
                    toDMUserDto(msg.sender()), toDMUserDto(msg.receiver()), msg.content()
            );
        }

        LiveMessageStat stat = conv.getLiveMessageStats().get(userId);
        boolean hasUnread = stat != null && stat.isHasUnread();

        return new ConversationDto(
                conv.getId().toString(),
                new DMUserDto(with.userId().toString(), with.name(), with.profileImageUrl()),
                lastMsgDto,
                hasUnread
        );
    }

    private DMUserDto toDMUserDto(DMUser dmUser) {
        return new DMUserDto(
                dmUser.userId().toString(), dmUser.name(), dmUser.profileImageUrl()
        );
    }
}

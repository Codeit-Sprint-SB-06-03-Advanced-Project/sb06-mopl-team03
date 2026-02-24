package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorResponseDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DMUserDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.SortOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadLiveMessageAdapter implements LoadLiveMessagePort {

    final LiveMessageRepository liveMessageRepository;

    @Override
    public CursorResponseDirectMessageDto findAll(
            UUID userId,
            UUID withUserId,
            CursorRequestDirectMessageDto request
    ) {
        List<LiveMessage> results = liveMessageRepository.findAll(userId, withUserId, request);
        long total = liveMessageRepository.countAll(userId, withUserId);

        int limit = request.limit();
        boolean hasNext = results.size() > limit;
        List<LiveMessage> page = hasNext ? results.subList(0, limit) : results;

        String nextCursor = null;
        String nextIdAfter = null;
        if (hasNext && !page.isEmpty()) {
            LiveMessage last = page.get(page.size() - 1);
            nextCursor = last.getCreatedAt().toString();
            nextIdAfter = last.getId().toString();
        }

        List<DirectMessageDto> data = page.stream()
                .map(msg -> new DirectMessageDto(
                        msg.getId().toString(),
                        null,
                        msg.getCreatedAt().toString(),
                        new DMUserDto(msg.getSender().userId().toString(), msg.getSender().name(), msg.getSender().profileImageUrl()),
                        new DMUserDto(msg.getReceiver().userId().toString(), msg.getReceiver().name(), msg.getReceiver().profileImageUrl()),
                        msg.getContent()
                ))
                .toList();

        return new CursorResponseDirectMessageDto(
                data,
                nextCursor,
                nextIdAfter,
                hasNext,
                total,
                request.sortBy(),
                SortOrder.valueOf(request.sortDirection())
        );
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestConversationDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LoadConversationAdapter implements LoadConversationPort {

    private final ConversationRepository conversationRepository;

    @Override
    public List<Conversation> findAll(
            UUID userId,
            String cursor,
            String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    ) {
        List<UUID> ids = conversationRepository.findAllIds(userId, cursor, idAfter, limit + 1, ascending, sortBy);
        if (ids.isEmpty()) {
            return List.of();
        }

        List<Conversation> conversations = conversationRepository.findAllByIds(ids);

        Map<UUID, Conversation> map = conversations.stream()
                .collect(Collectors.toMap(Conversation::getId, c -> c));
        return ids.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public long count(UUID userId) {
        return conversationRepository.count(userId);
    }

    @Override
    public Optional<Conversation> findById(UUID conversationId) {
        return conversationRepository.findConversationById(conversationId);
    }

    @Override
    public Optional<Conversation> findByParticipants(UUID userId, UUID withUserId) {
        return conversationRepository.findByParticipants(userId, withUserId);
    }
}

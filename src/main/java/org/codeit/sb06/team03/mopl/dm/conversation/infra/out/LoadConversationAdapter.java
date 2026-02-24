package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestConversationDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadConversationAdapter implements LoadConversationPort {

    private final ConversationRepository conversationRepository;

    @Override
    public List<Conversation> findAll(UUID userId, CursorRequestConversationDto request) {
        return conversationRepository.findAll(userId, request);
    }

    @Override
    public long count(UUID userId) {
        return conversationRepository.count(userId);
    }

    @Override
    public Optional<Conversation> findById(UUID userId, UUID conversationId) {
        return conversationRepository.findById(userId, conversationId);
    }

    @Override
    public Optional<Conversation> findEntityById(UUID conversationId) {
        return conversationRepository.findEntityById(conversationId);
    }

    @Override
    public Optional<Conversation> findByWith(UUID userId, UUID withUserId) {
        return conversationRepository.findByWith(userId, withUserId);
    }
}

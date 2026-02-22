package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadConversationAdapter implements LoadConversationPort {

    private final ConversationRepository conversationRepository;

    @Override
    public Optional<ConversationDto> findByWith(UUID withUserId) {
        return conversationRepository.findByWith(withUserId);
    }
}

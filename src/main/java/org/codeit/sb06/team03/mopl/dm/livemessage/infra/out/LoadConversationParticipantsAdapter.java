package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetConversationUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadConversationParticipantsPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadConversationParticipantsAdapter implements LoadConversationParticipantsPort {

    private final GetConversationUseCase getConversationUseCase;

    @Override
    public UUID findWithUserId(UUID userId, UUID conversationId) {
        ConversationDto conversation = getConversationUseCase.findById(userId, conversationId);
        return UUID.fromString(conversation.with().userId());
    }
}

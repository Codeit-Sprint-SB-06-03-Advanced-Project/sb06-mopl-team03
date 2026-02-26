package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetConversationUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadReceiverActivityPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadReceiverActivityAdapter implements LoadReceiverActivityPort {

    private final GetConversationUseCase getConversationUseCase;

    @Override
    public boolean isReceiverActive(UUID receiverId, UUID conversationId) {
        return getConversationUseCase.isParticipantActive(receiverId, conversationId);
    }
}

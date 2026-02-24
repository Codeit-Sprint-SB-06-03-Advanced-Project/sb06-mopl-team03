package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.SaveConversationPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveConversationAdapter implements SaveConversationPort {

    private final ConversationRepository conversationRepository;

    @Override
    public void save(Conversation conversation) {
        conversationRepository.save(conversation);
    }
}

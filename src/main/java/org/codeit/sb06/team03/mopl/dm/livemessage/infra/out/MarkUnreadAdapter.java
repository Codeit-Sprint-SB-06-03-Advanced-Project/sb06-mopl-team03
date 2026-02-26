package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.MessageReadUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MarkUnreadPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MarkUnreadAdapter implements MarkUnreadPort {

    private final MessageReadUseCase messageReadUseCase;

    @Override
    public void markAsUnread(UUID conversationId, UUID receiverId) {
        messageReadUseCase.markAsUnread(conversationId, receiverId);
    }
}

package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessagePassUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MessagePassPort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LiveMessagePassService implements MessagePassUseCase {

    private final MessagePassPort messagePassPort;

    @Override
    public void pass(UUID conversationId, UUID messageId, String content, Instant createdAt, DMUser sender, DMUser receiver) {
        messagePassPort.pass(conversationId, messageId, content, createdAt, sender, receiver);
    }
}

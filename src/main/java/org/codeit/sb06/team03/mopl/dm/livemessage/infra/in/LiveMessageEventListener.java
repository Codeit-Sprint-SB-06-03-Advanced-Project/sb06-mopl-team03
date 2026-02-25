package org.codeit.sb06.team03.mopl.dm.livemessage.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MessagePassPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.event.LiveMessageEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class LiveMessageEventListener {

    private final MessagePassPort messagePassPort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMessageSent(LiveMessageEvent.MessageSentEvent event) {
        messagePassPort.pass(
                event.getConversationId(),
                event.getReceiverId(),
                event.getMessageId(),
                event.getSenderId(),
                event.getContent(),
                event.getCreatedAt()
        );
    }
}
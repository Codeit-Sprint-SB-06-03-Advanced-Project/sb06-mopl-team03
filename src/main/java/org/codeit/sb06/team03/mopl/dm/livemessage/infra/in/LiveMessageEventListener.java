package org.codeit.sb06.team03.mopl.dm.livemessage.infra.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessagePassUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.event.LiveMessageEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class LiveMessageEventListener {

    private final MessagePassUseCase messagePassUseCase;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMessageSent(LiveMessageEvent.MessageSentEvent event) {
        try {
            messagePassUseCase.pass(
                    event.getConversationId(),
                    event.getMessageId(),
                    event.getContent(),
                    event.getCreatedAt(),
                    event.getSender(),
                    event.getReceiver()
            );
        } catch (Exception e) {
            log.error("DM WebSocket 전송 실패 - conversationId={}, messageId={}", event.getConversationId(), event.getMessageId(), e);
        }
    }
}
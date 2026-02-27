package org.codeit.sb06.team03.mopl.dm.livemessage.infra.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetConversationUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DMUserDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessagePassUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.event.LiveMessageEvent;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.codeit.sb06.team03.mopl.sse.application.SseUseCase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class LiveMessageEventListener {

    private final MessagePassUseCase messagePassUseCase;
    private final GetConversationUseCase getConversationUseCase;
    private final SseUseCase sseUseCase;
    private final CreateNotificationUseCase createNotificationUseCase;

    private static final String EVENT_NAME_DM = "direct-messages";
    private static final String EVENT_NAME_NOTIFICATION = "notifications";

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
            if (!getConversationUseCase.isParticipantActive(event.getReceiverId(), event.getConversationId())) {
                DMUserDto sender = DMUserDto.from(event.getSender());
                DirectMessageDto dto = new DirectMessageDto(
                        event.getMessageId().toString(),
                        event.getConversationId().toString(),
                        event.getCreatedAt().toString(),
                        sender,
                        DMUserDto.from(event.getReceiver()),
                        event.getContent()
                );
                sseUseCase.send(dto, EVENT_NAME_DM, event.getReceiverId());

                NotificationDto notificationDto = createNotificationUseCase.create(
                        event.getReceiverId(),
                        "[DM]" + sender.name(),
                        event.getContent(),
                        NotificationLevel.INFO
                );
                sseUseCase.send(notificationDto, EVENT_NAME_NOTIFICATION, event.getReceiverId());
            }
        } catch (Exception e) {
            log.error("DM WebSocket 전송 실패 - conversationId={}, messageId={}", event.getConversationId(), event.getMessageId(), e);
        }
    }
}
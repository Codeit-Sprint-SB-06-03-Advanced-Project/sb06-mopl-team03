package org.codeit.sb06.team03.mopl.account.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.event.AccountEvent;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.codeit.sb06.team03.mopl.sse.application.SseUseCase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AccountEventListener {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final SseUseCase sseUseCase;

    private static final String EVENT_NAME = "notifications";

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleRoleUpdatedEvent(AccountEvent.RoleUpdatedEvent event) {
        NotificationDto notificationDto = createNotificationUseCase.create(
                event.getAccountId(),
                "권한이 %s(으)로 변경되었어요.".formatted(event.getRole()),
                null,
                NotificationLevel.INFO
        );
        sseUseCase.send(notificationDto, EVENT_NAME, event.getAccountId());
    }
}

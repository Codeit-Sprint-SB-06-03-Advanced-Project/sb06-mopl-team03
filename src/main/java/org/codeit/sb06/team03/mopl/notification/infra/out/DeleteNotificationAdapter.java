package org.codeit.sb06.team03.mopl.notification.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.notification.application.out.DeleteNotificationPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DeleteNotificationAdapter implements DeleteNotificationPort {

    private final NotificationRepository repository;

    @Override
    public void deleteById(UUID notificationId) {
        repository.deleteById(notificationId);
    }
}

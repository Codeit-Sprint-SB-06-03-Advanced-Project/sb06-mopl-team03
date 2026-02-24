package org.codeit.sb06.team03.mopl.notification.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.notification.application.out.LoadSingleNotificationPort;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadSingleNotificationAdapter implements LoadSingleNotificationPort {

    private final NotificationRepository repository;

    @Override
    public Optional<Notification> load(UUID notificationId) {
        return repository.findById(notificationId);
    }

}

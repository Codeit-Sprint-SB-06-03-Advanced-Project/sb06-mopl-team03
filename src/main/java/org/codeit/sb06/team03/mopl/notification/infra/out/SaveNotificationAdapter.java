package org.codeit.sb06.team03.mopl.notification.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.notification.application.out.SaveNotificationPort;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SaveNotificationAdapter implements SaveNotificationPort {

    private final NotificationRepository repository;

    @Override
    public void save(Notification notification) {
        repository.save(notification);
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        repository.saveAll(notifications);
    }
}

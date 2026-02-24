package org.codeit.sb06.team03.mopl.notification.application.out;

import org.codeit.sb06.team03.mopl.notification.domain.Notification;

import java.util.List;

public interface SaveNotificationPort {
    void save(Notification notification);
    void saveAll(List<Notification> notifications);
}

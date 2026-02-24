package org.codeit.sb06.team03.mopl.notification.application.out;

import org.codeit.sb06.team03.mopl.notification.domain.Notification;

import java.util.Optional;
import java.util.UUID;

public interface LoadSingleNotificationPort {
    Optional<Notification> load(UUID notificationId);
}

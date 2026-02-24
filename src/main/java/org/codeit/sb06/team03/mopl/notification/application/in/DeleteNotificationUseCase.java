package org.codeit.sb06.team03.mopl.notification.application.in;

import java.util.UUID;

public interface DeleteNotificationUseCase {
    void delete(String notificationId, UUID receiverId);
}

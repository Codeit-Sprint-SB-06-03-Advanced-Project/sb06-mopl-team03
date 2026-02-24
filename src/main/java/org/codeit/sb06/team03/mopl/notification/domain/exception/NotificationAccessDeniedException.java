package org.codeit.sb06.team03.mopl.notification.domain.exception;

import java.util.UUID;

public class NotificationAccessDeniedException extends NotificationException {
    public NotificationAccessDeniedException(UUID notificationId, UUID userId) {
        super("알림 접근 권한이 없습니다. notificationId: %s, userId: %s".formatted(notificationId, userId));
    }
}

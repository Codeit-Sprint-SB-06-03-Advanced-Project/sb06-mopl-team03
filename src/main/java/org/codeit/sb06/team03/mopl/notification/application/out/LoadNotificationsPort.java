package org.codeit.sb06.team03.mopl.notification.application.out;

import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.out.CursorGetNotificationsCondition;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface LoadNotificationsPort {

    long countByReceiverId(UUID receiverId);

    Slice<NotificationDto> getNotifications(CursorGetNotificationsCondition condition);

}

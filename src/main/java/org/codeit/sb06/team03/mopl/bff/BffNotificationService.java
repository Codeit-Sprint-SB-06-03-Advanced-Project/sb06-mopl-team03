package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.notification.infra.in.CursorRequestNotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.in.CursorResponseNotificationDto;

import java.util.UUID;


public interface BffNotificationService {

    CursorResponseNotificationDto getNotifications(CursorRequestNotificationDto request, UUID receiverId);

    void deleteNotification(String id, UUID receiverId);
}

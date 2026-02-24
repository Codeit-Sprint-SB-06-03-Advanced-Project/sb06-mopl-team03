package org.codeit.sb06.team03.mopl.notification.application.in;

import org.codeit.sb06.team03.mopl.notification.infra.in.CursorRequestNotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.in.CursorResponseNotificationDto;

import java.util.UUID;

public interface GetNotificationsUseCase {
    CursorResponseNotificationDto get(CursorRequestNotificationDto request, UUID receiverId);
}

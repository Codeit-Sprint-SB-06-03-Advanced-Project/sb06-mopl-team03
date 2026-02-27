package org.codeit.sb06.team03.mopl.notification.application.in;

import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;

import java.util.List;
import java.util.UUID;

public interface CreateNotificationUseCase {

    NotificationDto create(UUID receiverId, String title, String content, NotificationLevel level);

    List<NotificationDto> createAll(List<UUID> receiverIds, String title, String content, NotificationLevel level);

}

package org.codeit.sb06.team03.mopl.notification.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.notification.application.out.LoadNotificationsPort;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadNotificationsAdapter implements LoadNotificationsPort {

    private final NotificationRepository repository;

    @Override
    public long countByReceiverId(UUID receiverId) {
        return repository.countByReceiverId(receiverId);
    }

    @Override
    public Slice<NotificationDto> getNotifications(CursorGetNotificationsCondition condition) {
        return repository.findAll(condition);
    }
}

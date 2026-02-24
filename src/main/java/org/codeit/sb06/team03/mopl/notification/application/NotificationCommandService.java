package org.codeit.sb06.team03.mopl.notification.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidIdentifierException;
import org.codeit.sb06.team03.mopl.notification.application.in.CreateNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.application.in.DeleteNotificationUseCase;
import org.codeit.sb06.team03.mopl.notification.application.out.DeleteNotificationPort;
import org.codeit.sb06.team03.mopl.notification.application.out.LoadSingleNotificationPort;
import org.codeit.sb06.team03.mopl.notification.application.out.SaveNotificationPort;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;
import org.codeit.sb06.team03.mopl.notification.domain.NotificationService;
import org.codeit.sb06.team03.mopl.notification.domain.exception.NotificationAccessDeniedException;
import org.codeit.sb06.team03.mopl.notification.domain.exception.NotificationNotFoundException;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationCommandService implements CreateNotificationUseCase, DeleteNotificationUseCase {

    private final SaveNotificationPort saveNotificationPort;
    private final LoadSingleNotificationPort loadSingleNotificationPort;
    private final DeleteNotificationPort deleteNotificationPort;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void create(UUID receiverId, String title, String content, NotificationLevel level) {
        Notification notification = notificationService.create(receiverId, title, content, level);
        saveNotificationPort.save(notification);
    }

    @Override
    public void createAll(List<UUID> receiverIds, String title, String content, NotificationLevel level) {
        List<Notification> notifications = receiverIds.stream()
                .map(id -> notificationService.create(id, title, content, level))
                .toList();
        saveNotificationPort.saveAll(notifications);
    }

    @Override
    public void delete(String notificationId, UUID receiverId) {

        UUID notificationUUID = parseUUID(notificationId);
        Notification notification = loadSingleNotificationPort.load(notificationUUID)
                .orElseThrow(() -> new NotificationNotFoundException(notificationUUID));

        if (!notification.getReceiverId().equals(receiverId)) {
            throw new NotificationAccessDeniedException(notificationUUID, receiverId);
        }
        deleteNotificationPort.deleteById(notificationUUID);
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }
}

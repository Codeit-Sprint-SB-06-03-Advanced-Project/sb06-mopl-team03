package org.codeit.sb06.team03.mopl.notification.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidIdentifierException;
import org.codeit.sb06.team03.mopl.notification.application.in.GetNotificationsUseCase;
import org.codeit.sb06.team03.mopl.notification.application.out.LoadNotificationsPort;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.codeit.sb06.team03.mopl.notification.infra.in.CursorRequestNotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.in.CursorResponseNotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.codeit.sb06.team03.mopl.notification.infra.out.CursorGetNotificationsCondition;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationQueryService implements GetNotificationsUseCase {

    private final LoadNotificationsPort loadNotificationsPort;

    @Override
    public CursorResponseNotificationDto get(CursorRequestNotificationDto request, UUID receiverId) {

        final UUID idAfter = request.idAfter() != null ? parseUUID(request.idAfter()): null;
        CursorGetNotificationsCondition condition = new CursorGetNotificationsCondition(
                receiverId,
                request.cursor(),
                idAfter,
                request.limit(),
                request.sortBy(),
                request.sortDirection().equals("DESCENDING")
        );

        Slice<NotificationDto> content = loadNotificationsPort.getNotifications(condition);

        String nextCursor = null;
        UUID nextIdAfter = null;
        boolean hasNext = content.hasNext();
        List<NotificationDto> data = content.getContent();
        if (hasNext) {
            nextCursor = data.get(content.getSize() - 1).createdAt().toString();
            nextIdAfter = data.get(content.getSize() - 1).id();
        }

        long totalCount = loadNotificationsPort.countByReceiverId(receiverId);
        return new CursorResponseNotificationDto(
                data,
                nextCursor,
                nextIdAfter,
                hasNext,
                totalCount,
                request.sortBy(),
                request.sortDirection()
        );
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }
}

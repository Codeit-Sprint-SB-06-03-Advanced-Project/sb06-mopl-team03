package org.codeit.sb06.team03.mopl.notification.infra.in;

import org.codeit.sb06.team03.mopl.notification.domain.NotificationLevel;

import java.time.Instant;
import java.util.UUID;

public record NotificationDto (
        UUID id,
        Instant createdAt,
        UUID receiverId,
        String title,
        String content,
        NotificationLevel level
) {

}

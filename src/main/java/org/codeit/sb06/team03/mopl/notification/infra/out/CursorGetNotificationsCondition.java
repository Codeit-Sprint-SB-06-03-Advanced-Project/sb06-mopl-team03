package org.codeit.sb06.team03.mopl.notification.infra.out;

import java.util.UUID;

public record CursorGetNotificationsCondition(
        UUID receiverId,
        String cursor,
        UUID idAfter,
        int limit,
        String sortBy,
        boolean descending
) {
}

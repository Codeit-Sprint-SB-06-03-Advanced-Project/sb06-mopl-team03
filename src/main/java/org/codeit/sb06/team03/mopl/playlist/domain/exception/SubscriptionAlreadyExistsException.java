package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class SubscriptionAlreadyExistsException extends PlaylistException {
    public SubscriptionAlreadyExistsException(UUID playlistId, UUID userId) {
        super("Subscription already exists. playlistId: %s , userId: %s".formatted(playlistId, userId));
    }
}

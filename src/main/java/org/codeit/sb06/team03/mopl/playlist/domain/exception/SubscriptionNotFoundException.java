package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class SubscriptionNotFoundException extends PlaylistException {
    public SubscriptionNotFoundException(UUID playlistId, UUID subscriberId) {
        super("Subscription not found. playlistId: %s, subscriberId: %s".formatted(playlistId, subscriberId));
    }
}

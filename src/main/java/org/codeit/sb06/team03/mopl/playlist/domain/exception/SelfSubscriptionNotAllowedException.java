package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class SelfSubscriptionNotAllowedException extends PlaylistException {
    public SelfSubscriptionNotAllowedException(UUID playlistId, UUID ownerId) {
        super("Self subscription is not allowed. playlistId: %s, ownerId: %s".formatted(playlistId, ownerId));
    }
}

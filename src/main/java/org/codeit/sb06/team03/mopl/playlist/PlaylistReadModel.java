package org.codeit.sb06.team03.mopl.playlist;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.time.Instant;
import java.util.UUID;

public record PlaylistReadModel(
        UUID id,
        UUID ownerId,
        String title,
        String description,
        Instant updatedAt,
        long subscriberCount,
        long contentCount
) {
    public static PlaylistReadModel from(Playlist playlist) {
        return new PlaylistReadModel(
                playlist.getId(),
                playlist.getOwnerId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getUpdatedAt(),
                playlist.getSubscriberCount(),
                playlist.getContentCount()
        );
    }
}
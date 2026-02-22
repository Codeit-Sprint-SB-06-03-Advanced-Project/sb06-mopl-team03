package org.codeit.sb06.team03.mopl.playlist.infra.in.response;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.time.Instant;
import java.util.UUID;

public record PlaylistDto(
        UUID id,
        UserSummaryDto owner,
        String title,
        String description,
        Instant updatedAt,
        long subscriberCount,
        boolean subscribedByMe
        //List<ContentDto> contents // TODO
) {

    public static PlaylistDto toDto(Playlist playlist, UserSummaryDto owner, boolean subscribedByMe/*, List<ContentDto> contents*/) {
        return new PlaylistDto(
                playlist.getId(),
                owner,
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getUpdatedAt(),
                playlist.getSubscriberCount(),
                subscribedByMe
                // contents
        );
    }
}

package org.codeit.sb06.team03.mopl.playlist.infra.in.response;

import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PlaylistDto(
        UUID id,
        UserSummaryDto owner,
        String title,
        String description,
        Instant updatedAt,
        long subscriberCount,
        boolean subscribedByMe,
        List<ContentResult> contents // TODO
) {

    public static PlaylistDto toDto(Playlist playlist, UserSummaryDto owner, boolean subscribedByMe, List<ContentResult> contents) {
        return new PlaylistDto(
                playlist.getId(),
                owner,
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getUpdatedAt(),
                playlist.getSubscriberCount(),
                subscribedByMe,
                contents
        );
    }

    public static PlaylistDto toDto(PlaylistReadModel readModel, UserSummaryDto owner, boolean subscribedByMe, List<ContentResult> contents) {
        return new PlaylistDto(
                readModel.id(),
                owner,
                readModel.title(),
                readModel.description(),
                readModel.updatedAt(),
                readModel.subscriberCount(),
                subscribedByMe,
                contents
        );
    }
}

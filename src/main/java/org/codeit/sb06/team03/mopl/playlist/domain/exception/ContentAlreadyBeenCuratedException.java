package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class ContentAlreadyBeenCuratedException extends PlaylistException {
    public ContentAlreadyBeenCuratedException(UUID playlistId, UUID contentId) {
        super("Content already been curated. playlistId: %s, contentId: %s".formatted(playlistId, contentId));
    }
}

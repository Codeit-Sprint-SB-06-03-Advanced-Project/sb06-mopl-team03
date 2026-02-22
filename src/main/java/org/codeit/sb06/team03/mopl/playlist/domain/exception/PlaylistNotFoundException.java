package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class PlaylistNotFoundException extends PlaylistException {
    public PlaylistNotFoundException(UUID id) {
        super("Playlist not found: %s".formatted(id));
    }
}

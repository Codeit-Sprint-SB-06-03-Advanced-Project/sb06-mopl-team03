package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class CurationNotFoundException extends RuntimeException {
    public CurationNotFoundException(UUID playlistId, UUID contentId) {
        super("Curation not found. playlistId: %s, contentId: %s".formatted(playlistId, contentId));
    }
}

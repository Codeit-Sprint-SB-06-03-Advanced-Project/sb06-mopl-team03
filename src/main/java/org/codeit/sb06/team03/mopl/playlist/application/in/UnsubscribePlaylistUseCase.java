package org.codeit.sb06.team03.mopl.playlist.application.in;

import java.util.UUID;

public interface UnsubscribePlaylistUseCase {
    void unsubscribe(String playlistId, UUID userId);
}

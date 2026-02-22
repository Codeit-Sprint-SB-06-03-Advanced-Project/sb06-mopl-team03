package org.codeit.sb06.team03.mopl.playlist.application.in;

import java.util.UUID;

public interface DeleteContentFromCurationUseCase {
    void deleteContentFromPlaylist(String playlistId, String contentId, UUID ownerId);
}

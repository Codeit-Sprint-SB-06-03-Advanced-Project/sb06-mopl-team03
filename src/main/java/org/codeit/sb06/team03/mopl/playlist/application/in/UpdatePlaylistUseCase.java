package org.codeit.sb06.team03.mopl.playlist.application.in;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.util.UUID;

public interface UpdatePlaylistUseCase {

    Playlist update(String playlistId, UpdatePlaylistCommand command, UUID ownerId);

}

package org.codeit.sb06.team03.mopl.playlist.application.in;

import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;

import java.util.UUID;

public interface GetSinglePlaylistUseCase {

    PlaylistReadModel get(String playlistId, UUID viewerId);

}

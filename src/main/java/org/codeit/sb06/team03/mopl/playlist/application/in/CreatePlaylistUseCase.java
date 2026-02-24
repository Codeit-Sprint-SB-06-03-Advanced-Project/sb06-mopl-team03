package org.codeit.sb06.team03.mopl.playlist.application.in;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.util.UUID;

public interface CreatePlaylistUseCase {

    Playlist create(CreatePlaylistCommand command, UUID ownerId);

}
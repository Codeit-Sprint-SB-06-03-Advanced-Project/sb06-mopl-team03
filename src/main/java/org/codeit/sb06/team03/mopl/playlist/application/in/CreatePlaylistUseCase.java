package org.codeit.sb06.team03.mopl.playlist.application.in;

import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;

import java.util.UUID;

public interface CreatePlaylistUseCase {

    PlaylistDto create(CreatePlaylistCommand command, UUID ownerId);

}

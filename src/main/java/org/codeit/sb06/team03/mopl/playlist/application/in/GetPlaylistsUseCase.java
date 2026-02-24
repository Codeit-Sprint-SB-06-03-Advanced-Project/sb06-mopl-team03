package org.codeit.sb06.team03.mopl.playlist.application.in;

import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface GetPlaylistsUseCase {

    Slice<PlaylistReadModel> get(CursorRequestPlaylistDto request, UUID viewerId);
}

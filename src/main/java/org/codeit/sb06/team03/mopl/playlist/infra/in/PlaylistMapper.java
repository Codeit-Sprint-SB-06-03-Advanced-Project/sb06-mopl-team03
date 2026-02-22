package org.codeit.sb06.team03.mopl.playlist.infra.in;

import org.codeit.sb06.team03.mopl.playlist.application.in.CreatePlaylistCommand;
import org.codeit.sb06.team03.mopl.playlist.application.in.UpdatePlaylistCommand;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {

    public CreatePlaylistCommand toCommand(PlaylistCreateRequest request) {
        return new CreatePlaylistCommand(request.title(), request.description());
    }

    public UpdatePlaylistCommand toCommand(PlaylistUpdateRequest request) {
        return new UpdatePlaylistCommand(request.title(), request.description());
    }

}

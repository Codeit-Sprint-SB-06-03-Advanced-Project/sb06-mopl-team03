package org.codeit.sb06.team03.mopl.playlist.application.out;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;

import java.util.UUID;

public interface SavePlaylistPort {

    void save(Playlist playlist);

    void delete(UUID playlistId);

}

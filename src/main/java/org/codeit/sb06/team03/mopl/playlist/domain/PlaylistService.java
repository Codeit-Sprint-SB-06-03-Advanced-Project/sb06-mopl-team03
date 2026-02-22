package org.codeit.sb06.team03.mopl.playlist.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaylistService {

    public Playlist create(String title, String description, UUID ownerId) {
        return Playlist.create(title, description, ownerId);
    }

    public Playlist update(Playlist playlist, String title, String description) {
        playlist.update(title, description);
        return playlist;
    }

}

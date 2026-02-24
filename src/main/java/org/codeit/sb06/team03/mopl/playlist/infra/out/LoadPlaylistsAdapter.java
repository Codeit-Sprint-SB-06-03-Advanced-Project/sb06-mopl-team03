package org.codeit.sb06.team03.mopl.playlist.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadPlaylistsPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadPlaylistsAdapter implements LoadPlaylistsPort {

    private final PlaylistRepository playlistRepository;

    @Override
    public Slice<PlaylistReadModel> findAll(
            String keywordLike,
            UUID ownerIdEqual,
            UUID subscriberIdEqual,
            String cursor,
            UUID idAfter,
            int limit,
            String sortDirection,
            String sortBy
    ) {
        return playlistRepository.findAll(
                keywordLike,
                ownerIdEqual,
                subscriberIdEqual,
                cursor,
                idAfter,
                limit,
                sortDirection,
                sortBy
        );
    }

}

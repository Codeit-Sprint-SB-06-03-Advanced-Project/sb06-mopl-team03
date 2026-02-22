package org.codeit.sb06.team03.mopl.playlist.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.application.out.SaveCurationPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Curation;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.CurationId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SaveCurationAdapter implements SaveCurationPort {

    private final CurationRepository repository;

    @Override
    public void save(Curation curation) {
        repository.save(curation);
    }

    @Override
    public void delete(CurationId id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByPlaylistId(UUID playlistId) {
        repository.deleteAllByPlaylistId(playlistId);
    }

    @Override
    public void deleteAllByContentId(UUID contentId) {
        repository.deleteAllByContentId(contentId);
    }
}

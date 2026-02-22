package org.codeit.sb06.team03.mopl.playlist.application.out;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Curation;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.CurationId;

import java.util.UUID;

public interface SaveCurationPort {

    void save(Curation curation);

    void delete(CurationId id);

    void deleteAllByPlaylistId(UUID playlistId);

    void deleteAllByContentId(UUID contentId);
}

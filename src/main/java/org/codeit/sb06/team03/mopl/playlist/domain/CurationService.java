package org.codeit.sb06.team03.mopl.playlist.domain;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Curation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurationService {

    public Curation create(UUID playlistId, UUID subscriptionId) {
        return Curation.create(playlistId, subscriptionId);
    }

}

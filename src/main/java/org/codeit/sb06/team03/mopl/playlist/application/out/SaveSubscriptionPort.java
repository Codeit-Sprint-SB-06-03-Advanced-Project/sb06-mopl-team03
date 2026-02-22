package org.codeit.sb06.team03.mopl.playlist.application.out;

import org.codeit.sb06.team03.mopl.playlist.domain.entity.Subscription;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;

import java.util.UUID;

public interface SaveSubscriptionPort {

    void save(Subscription subscription);

    void delete(SubscriptionId id);

    void deleteAllByPlaylistId(UUID playlistId);

    void deleteAllByAccountId(UUID accountId);

}

package org.codeit.sb06.team03.mopl.playlist.application.in;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GetSubscriptionUseCase {

    boolean isSubscribed(String playlistId, UUID viewerId);

    Map<UUID, Boolean> isSubscribed(List<UUID> playlistIds, UUID viewerId);

}

package org.codeit.sb06.team03.mopl.content.application.out;

import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.content.Content;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadContentPort {

    Optional<Content> findByIdWithTags(UUID contentId);

    List<SessionDetails> findSessionsDetails(WatchingSessionCursorQuery query);

    long countByContentIdAndWatcherNameLike(UUID contentId, String watcherName);
}

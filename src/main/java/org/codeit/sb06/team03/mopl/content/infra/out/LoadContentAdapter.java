package org.codeit.sb06.team03.mopl.content.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.content.Content;
import org.codeit.sb06.team03.mopl.content.application.out.LoadContentPort;
import org.codeit.sb06.team03.mopl.content.application.out.WatchingSessionCursorQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoadContentAdapter implements LoadContentPort {

    private final ContentRepository contentRepository;

    @Override
    public Optional<Content> findByIdWithTags(UUID contentId) {
        return contentRepository.findByIdWithTags(contentId);
    }

    @Override
    public List<SessionDetails> findSessionsDetails(WatchingSessionCursorQuery query) {
        return contentRepository.findSessionsDetails(query);
    }

    @Override
    public long countByContentIdAndWatcherNameLike(UUID contentId, String watcherName) {
        return contentRepository.countByContentIdAndWatcherNameLike(contentId, watcherName);
    }
}

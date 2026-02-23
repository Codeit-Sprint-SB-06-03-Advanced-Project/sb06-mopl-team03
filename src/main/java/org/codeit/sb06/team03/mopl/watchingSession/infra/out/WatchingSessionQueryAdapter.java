package org.codeit.sb06.team03.mopl.watchingSession.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.liveChat.application.out.LiveChatWatchingSessionQueryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WatchingSessionQueryAdapter implements LiveChatWatchingSessionQueryPort {

    private final WatchingSessionRepository watchingSessionRepository;

    @Override
    public int countByLiveChatId(UUID liveChatId) {
        return watchingSessionRepository.countByLiveChatId(liveChatId);
    }
}

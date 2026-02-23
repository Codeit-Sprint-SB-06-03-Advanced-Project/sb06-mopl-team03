package org.codeit.sb06.team03.mopl.liveChat.application.out;

import org.codeit.sb06.team03.mopl.liveChat.domain.LiveChat;

import java.util.Optional;
import java.util.UUID;

public interface LoadLiveChatPort {

    Optional<LiveChat> findById(UUID liveChatId);

    boolean existsById(UUID liveChatId);
}

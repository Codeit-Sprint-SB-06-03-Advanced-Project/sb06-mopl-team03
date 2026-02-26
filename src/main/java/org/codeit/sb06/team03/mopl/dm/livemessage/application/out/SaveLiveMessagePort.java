package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;

import java.util.UUID;

public interface SaveLiveMessagePort {

    LiveMessage save(LiveMessage liveMessage);

    void markAsRead(UUID messageId);
}
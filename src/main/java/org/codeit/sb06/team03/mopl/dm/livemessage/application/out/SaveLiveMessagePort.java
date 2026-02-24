package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;

public interface SaveLiveMessagePort {
    LiveMessage save(LiveMessage liveMessage);
}

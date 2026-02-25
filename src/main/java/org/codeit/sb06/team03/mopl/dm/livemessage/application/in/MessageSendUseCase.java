package org.codeit.sb06.team03.mopl.dm.livemessage.application.in;

import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;

public interface MessageSendUseCase {
    LiveMessage send(MessageSendCommand command);
}

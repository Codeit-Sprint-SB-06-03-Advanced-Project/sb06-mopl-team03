package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadLiveMessageStatPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadReceiverActivityPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadReceiverActivityAdapter implements LoadReceiverActivityPort {

    private final LoadLiveMessageStatPort loadLiveMessageStatPort;

    @Override
    public boolean isReceiverActive(UUID conversationId, UUID receiverId) {
        return loadLiveMessageStatPort.isActive(conversationId, receiverId);
    }
}

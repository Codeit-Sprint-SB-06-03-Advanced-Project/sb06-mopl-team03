package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.LiveMessageNotFoundException;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.SaveLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SaveLiveMessageAdapter implements SaveLiveMessagePort {

    private final LiveMessageRepository liveMessageRepository;

    @Override
    public LiveMessage save(LiveMessage liveMessage) {
        return liveMessageRepository.save(liveMessage);
    }

    @Override
    public void markAsRead(UUID messageId) {
        LiveMessage message = liveMessageRepository.findById(messageId)
                .orElseThrow(() -> new LiveMessageNotFoundException(messageId));
        message.markAsRead();
        liveMessageRepository.save(message);
    }
}
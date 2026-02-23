package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.liveChat.application.out.SaveLiveChatPort;
import org.codeit.sb06.team03.mopl.liveChat.domain.LiveChat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveLiveChatAdapter implements SaveLiveChatPort {

    private final LiveChatRepository liveChatRepository;

    @Override
    public LiveChat save(LiveChat liveChat) {
        return liveChatRepository.save(liveChat);
    }
}

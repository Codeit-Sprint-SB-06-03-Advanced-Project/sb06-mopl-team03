package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.liveChat.application.out.LoadLiveChatPort;
import org.codeit.sb06.team03.mopl.liveChat.domain.LiveChat;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoadLiveChatAdapter implements LoadLiveChatPort {

    private final LiveChatRepository liveChatRepository;

    @Override
    public Optional<LiveChat> findById(UUID liveChatId) {
        return liveChatRepository.findById(liveChatId);
    }

    @Override
    public boolean existsById(UUID liveChatId) {
        return liveChatRepository.existsById(liveChatId);
    }
}

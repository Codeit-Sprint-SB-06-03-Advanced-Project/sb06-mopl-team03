package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.liveChat.application.out.DeleteLiveChatPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteLiveChatAdapter implements DeleteLiveChatPort {

    private final LiveChatRepository liveChatRepository;

    @Override
    public void deleteById(UUID contentId) {
        liveChatRepository.deleteById(contentId);
    }
}

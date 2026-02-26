package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LiveMessageService {

    public LiveMessage create(UUID conversationId, UUID senderId, UUID receiverId, String content, DMUser sender, DMUser receiver) {
        return LiveMessage.create(conversationId, senderId, receiverId, content, sender, receiver);
    }
}
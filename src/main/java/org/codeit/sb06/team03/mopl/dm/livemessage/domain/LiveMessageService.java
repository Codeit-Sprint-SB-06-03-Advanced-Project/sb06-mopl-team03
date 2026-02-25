package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LiveMessageService {

    public LiveMessage create(UUID conversationId, UUID senderId, UUID receiverId, String content) {
        return LiveMessage.create(conversationId, senderId, receiverId, content);
    }
}
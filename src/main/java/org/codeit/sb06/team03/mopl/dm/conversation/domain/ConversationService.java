package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.policy.LiveMessagingStatsPolicy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationService {
    private final LiveMessagingStatsPolicy liveMessagingStatsPolicy;

    public Conversation create(UUID userId, UUID withUserID) {
        return Conversation.create(userId, withUserID);
    }
}

package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.policy.LiveMessagingStatsPolicy;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConversationService {
    private final LiveMessagingStatsPolicy liveMessagingStatsPolicy;

}

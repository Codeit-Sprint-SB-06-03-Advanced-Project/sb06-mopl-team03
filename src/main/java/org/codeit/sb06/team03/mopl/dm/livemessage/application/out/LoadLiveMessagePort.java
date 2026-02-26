package org.codeit.sb06.team03.mopl.dm.livemessage.application.out;

import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadLiveMessagePort {

    List<LiveMessage> findAll(
            UUID conversationId,
            String cursor,
            String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    );

    long count(UUID conversationId);

    Optional<LiveMessage> findById(UUID messageId);

    Optional<LiveMessage> findLatestByConversationId(UUID conversationId);
}
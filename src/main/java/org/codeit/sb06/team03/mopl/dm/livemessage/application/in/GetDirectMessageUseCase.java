package org.codeit.sb06.team03.mopl.dm.livemessage.application.in;

import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;

import java.util.List;
import java.util.UUID;

public interface GetDirectMessageUseCase {
    List<LiveMessage> findAll(UUID conversationId, String cursor, String idAfter, int limit,
                              String sortDirection, String sortBy);
    long countAll(UUID conversationId);
}
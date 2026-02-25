package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LiveMessageQueryService implements GetDirectMessageUseCase {

    private final LoadLiveMessagePort loadLiveMessagePort;

    @Override
    public List<LiveMessage> findAll(UUID conversationId, String cursor, String idAfter, int limit,
                                     String sortDirection, String sortBy) {
        boolean ascending = "ASC".equalsIgnoreCase(sortDirection);
        return loadLiveMessagePort.findAll(conversationId, cursor, idAfter, limit + 1, ascending, sortBy);
    }

    @Override
    public long countAll(UUID conversationId) {
        return loadLiveMessagePort.count(conversationId);
    }
}
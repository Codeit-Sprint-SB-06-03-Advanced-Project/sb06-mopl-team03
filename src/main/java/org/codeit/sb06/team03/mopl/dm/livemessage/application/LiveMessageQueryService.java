package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.GetDirectMessageUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LiveMessageQueryService implements GetDirectMessageUseCase {

    private final LoadLiveMessagePort loadLiveMessagePort;

    @Override
    public List<LiveMessage> findAll(
            UUID conversationId,
            String cursor,
            String idAfter,
            int limit,
            String sortDirection,
            String sortBy
    ) {
        boolean ascending = "ASC".equalsIgnoreCase(sortDirection);
        return loadLiveMessagePort.findAll(conversationId, cursor, idAfter, limit, ascending, sortBy);
    }

    @Override
    public long countAll(UUID conversationId) {
        return loadLiveMessagePort.count(conversationId);
    }

    @Override
    public Optional<LiveMessage> findLatestByConversationId(UUID conversationId) {
        return loadLiveMessagePort.findLatestByConversationId(conversationId);
    }

    @Override
    public Map<UUID, LiveMessage> findLatestByConversationIds(Set<UUID> conversationIds) {
        return conversationIds.stream()
                .map(id -> Map.entry(id, loadLiveMessagePort.findLatestByConversationId(id)))
                .filter(e -> e.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}
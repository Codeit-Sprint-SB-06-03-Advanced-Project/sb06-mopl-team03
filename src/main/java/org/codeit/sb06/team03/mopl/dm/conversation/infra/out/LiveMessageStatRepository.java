package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.QLiveMessageStat;

import java.util.List;
import java.util.UUID;

public interface LiveMessageStatRepository extends QuerydslJpaRepository<LiveMessageStat, UUID> {

    default boolean isActive(UUID conversationId, UUID accountId) {
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;
        return selectFrom(stat)
                .where(
                        stat.conversation.id.eq(conversationId),
                        stat.accountId.eq(accountId),
                        stat.activity.isTrue()
                )
                .fetchFirst() != null;
    }

    default List<UUID> findConversationIdsByAccountId(UUID accountId) {
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;
        return select(stat.conversation.id)
                .from(stat)
                .where(stat.accountId.eq(accountId))
                .fetch();
    }
}

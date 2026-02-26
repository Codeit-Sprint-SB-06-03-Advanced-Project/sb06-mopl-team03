package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.common.infra.CursorUtils;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.QLiveMessage;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LiveMessageRepository extends QuerydslJpaRepository<LiveMessage, UUID> {

    default List<LiveMessage> findAll(
            UUID conversationId,
            @Nullable String cursor,
            @Nullable String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    ) {
        QLiveMessage m = QLiveMessage.liveMessage;

        BooleanExpression where = m.conversationId.eq(conversationId);
        BooleanExpression cursorCond = CursorUtils.buildCursorCondition(m.createdAt, m.id, cursor, idAfter, ascending);
        if (cursorCond != null) where = where.and(cursorCond);

        return select(m).from(m)
                .where(where)
                .orderBy(ascending ? m.createdAt.asc() : m.createdAt.desc(),
                        ascending ? m.id.asc() : m.id.desc())
                .limit(limit)
                .fetch();
    }

    default long count(UUID conversationId) {
        QLiveMessage m = QLiveMessage.liveMessage;
        Long result = select(m.count()).from(m)
                .where(m.conversationId.eq(conversationId))
                .fetchOne();
        return result == null ? 0L : result;
    }

    default Optional<LiveMessage> findLatestByConversationId(UUID conversationId) {
        QLiveMessage m = QLiveMessage.liveMessage;
        return Optional.ofNullable(
                select(m).from(m)
                        .where(m.conversationId.eq(conversationId))
                        .orderBy(m.createdAt.desc(), m.id.desc())
                        .fetchFirst()
        );
    }
}

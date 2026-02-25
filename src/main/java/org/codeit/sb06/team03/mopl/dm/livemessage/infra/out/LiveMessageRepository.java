package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.QLiveMessage;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LiveMessageRepository extends QuerydslJpaRepository<LiveMessage, UUID> {

    default List<LiveMessage> findAll(
            UUID conversationId,
            String cursor,
            String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    ) {
        QLiveMessage m = QLiveMessage.liveMessage;

        BooleanExpression where = m.conversationId.eq(conversationId);
        BooleanExpression cursorCond = buildCursorCondition(m, cursor, idAfter, ascending);
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

    private BooleanExpression buildCursorCondition(
            QLiveMessage m,
            String cursor,
            String idAfter,
            boolean isAsc
    ) {
        if (cursor == null) return null;
        Instant cursorTime = Instant.parse(cursor);
        UUID idAfterUuid = idAfter == null ? null : UUID.fromString(idAfter);

        if (isAsc) {
            BooleanExpression cond = m.createdAt.gt(cursorTime);
            if (idAfterUuid != null)
                cond = cond.or(m.createdAt.eq(cursorTime).and(m.id.gt(idAfterUuid)));
            return cond;
        } else {
            BooleanExpression cond = m.createdAt.lt(cursorTime);
            if (idAfterUuid != null)
                cond = cond.or(m.createdAt.eq(cursorTime).and(m.id.lt(idAfterUuid)));
            return cond;
        }
    }
}

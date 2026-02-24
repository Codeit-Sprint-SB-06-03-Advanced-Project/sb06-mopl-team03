package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestDirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.QLiveMessage;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface LiveMessageRepository extends QuerydslJpaRepository<LiveMessage, UUID> {

    default List<LiveMessage> findAll(UUID aUserId, UUID bUserId,
                                      CursorRequestDirectMessageDto request) {
        QLiveMessage m = QLiveMessage.liveMessage;
        boolean isAsc = "ASCENDING".equals(request.sortDirection());

        BooleanExpression pair =
                m.sender.userId.eq(aUserId).and(m.receiver.userId.eq(bUserId))
                        .or(m.sender.userId.eq(bUserId).and(m.receiver.userId.eq(aUserId)));

        BooleanExpression cursor = buildCursorCondition(m, request, isAsc);
        BooleanExpression where = cursor != null ? pair.and(cursor) : pair;

        return select(m).from(m)
                .where(where)
                .orderBy(isAsc ? m.createdAt.asc() : m.createdAt.desc(),
                        isAsc ? m.id.asc()        : m.id.desc())
                .limit(request.limit() + 1L)
                .fetch();
    }

    default long countAll(UUID aUserId, UUID bUserId) {
        QLiveMessage m = QLiveMessage.liveMessage;
        BooleanExpression pair =
                m.sender.userId.eq(aUserId).and(m.receiver.userId.eq(bUserId))
                        .or(m.sender.userId.eq(bUserId).and(m.receiver.userId.eq(aUserId)));
        Long result = select(m.count()).from(m).where(pair).fetchOne();
        return result == null ? 0L : result;
    }

    private BooleanExpression buildCursorCondition(QLiveMessage m,
                                                   CursorRequestDirectMessageDto request,
                                                   boolean isAsc) {
        if (request.cursor() == null) return null;
        Instant cursorTime = Instant.parse(request.cursor());
        UUID idAfter = request.idAfter() == null ? null : UUID.fromString(request.idAfter());

        if (isAsc) {
            BooleanExpression cond = m.createdAt.gt(cursorTime);
            if (idAfter != null)
                cond = cond.or(m.createdAt.eq(cursorTime).and(m.id.gt(idAfter)));
            return cond;
        } else {
            BooleanExpression cond = m.createdAt.lt(cursorTime);
            if (idAfter != null)
                cond = cond.or(m.createdAt.eq(cursorTime).and(m.id.lt(idAfter)));
            return cond;
        }
    }
}
package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.QConversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.QLiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.CursorRequestConversationDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends QuerydslJpaRepository<Conversation, UUID> {

    default Optional<Conversation> findByParticipants(UUID userId, UUID withUserId) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat statA = new QLiveMessageStat("statA");
        QLiveMessageStat statB = new QLiveMessageStat("statB");

        return Optional.ofNullable(
                select(conv).from(conv)
                        .join(statA).on(statA.conversation.eq(conv).and(statA.accountId.eq(userId)))
                        .join(statB).on(statB.conversation.eq(conv).and(statB.accountId.eq(withUserId)))
                        .fetchFirst()
        );
    }

    default List<Conversation> findAll(UUID userId, String cursor, String idAfter,
                                       int limit, boolean ascending, String sortBy) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;

        BooleanExpression cursorCond = buildCursorCondition(conv, cursor, idAfter, ascending);

        return select(conv).from(conv)
                .join(stat).on(stat.conversation.eq(conv).and(stat.accountId.eq(userId)))
                .where(cursorCond)
                .orderBy(ascending ? conv.createdAt.asc() : conv.createdAt.desc(),
                        ascending ? conv.id.asc() : conv.id.desc())
                .limit(limit)
                .fetch();
    }

    default long count(UUID userId) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;
        Long result = select(conv.count()).from(conv)
                .join(stat).on(stat.conversation.eq(conv).and(stat.accountId.eq(userId)))
                .fetchOne();
        return result == null ? 0L : result;
    }

    default Optional<Conversation> findConversationById(UUID conversationId) {
        QConversation conv = QConversation.conversation;
        return Optional.ofNullable(
                select(conv).from(conv)
                        .where(conv.id.eq(conversationId))
                        .fetchFirst()
        );
    }

    private BooleanExpression buildCursorCondition(QConversation conv,
                                                   String cursor, String idAfter,
                                                   boolean isAsc) {
        if (cursor == null) return null;
        Instant cursorTime = Instant.parse(cursor);
        UUID idAfterUuid = idAfter == null ? null : UUID.fromString(idAfter);

        if (isAsc) {
            BooleanExpression condition = conv.createdAt.gt(cursorTime);
            if (idAfterUuid != null)
                condition = condition.or(conv.createdAt.eq(cursorTime).and(conv.id.gt(idAfterUuid)));
            return condition;
        } else {
            BooleanExpression condition = conv.createdAt.lt(cursorTime);
            if (idAfterUuid != null)
                condition = condition.or(conv.createdAt.eq(cursorTime).and(conv.id.lt(idAfterUuid)));
            return condition;
        }
    }
}

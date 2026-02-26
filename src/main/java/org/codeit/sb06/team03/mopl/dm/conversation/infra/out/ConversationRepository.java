package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.common.infra.CursorUtils;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.QConversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.QLiveMessageStat;
import org.springframework.lang.Nullable;

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
                        .leftJoin(conv.liveMessageStats).fetchJoin()
                        .fetchFirst()
        );
    }

    default List<UUID> findAllIds(
            UUID userId,
            @Nullable String cursor,
            @Nullable String idAfter,
            int limit,
            boolean ascending,
            String sortBy
    ) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;

        BooleanExpression cursorCond = CursorUtils.buildCursorCondition(
                conv.createdAt, conv.id, cursor, idAfter, ascending
        );

        return select(conv.id).from(conv)
                .join(stat).on(stat.conversation.eq(conv).and(stat.accountId.eq(userId)))
                .where(cursorCond)
                .orderBy(ascending ? conv.createdAt.asc() : conv.createdAt.desc(),
                        ascending ? conv.id.asc() : conv.id.desc())
                .limit(limit)
                .fetch();
    }

    default List<Conversation> findAllByIds(List<UUID> ids) {
        if (ids.isEmpty()) return List.of();

        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;

        return select(conv).from(conv)
                .leftJoin(conv.liveMessageStats, stat).fetchJoin()
                .where(conv.id.in(ids))
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
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;
        return Optional.ofNullable(
                select(conv).from(conv)
                        .leftJoin(conv.liveMessageStats, stat).fetchJoin()
                        .where(conv.id.eq(conversationId))
                        .fetchFirst()
        );
    }
}

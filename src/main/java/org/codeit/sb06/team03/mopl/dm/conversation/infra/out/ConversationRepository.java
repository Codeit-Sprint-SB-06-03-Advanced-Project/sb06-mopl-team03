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

    default Optional<Conversation> findByWith(UUID userId, UUID withUserId) {
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

    default List<Conversation> findAll(UUID userId, CursorRequestConversationDto request) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;
        boolean isAsc = "ASCENDING".equals(request.sortDirection());

        BooleanExpression cursor = buildCursorCondition(conv, request, isAsc);

        return select(conv).from(conv)
                .join(stat).on(stat.conversation.eq(conv).and(stat.accountId.eq(userId)))
                .where(cursor)
                .orderBy(isAsc ? conv.createdAt.asc() : conv.createdAt.desc(),
                        isAsc ? conv.id.asc() : conv.id.desc())
                .limit(request.limit() + 1L)
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

    default Optional<Conversation> findById(UUID userId, UUID conversationId) {
        QConversation conv = QConversation.conversation;
        QLiveMessageStat stat = QLiveMessageStat.liveMessageStat;

        return Optional.ofNullable(
                select(conv).from(conv)
                        .join(stat).on(stat.conversation.eq(conv).and(stat.accountId.eq(userId)))
                        .where(conv.id.eq(conversationId))
                        .fetchFirst()
        );
    }

    default Optional<Conversation> findEntityById(UUID conversationId) {
        QConversation conv = QConversation.conversation;
        return Optional.ofNullable(
                select(conv).from(conv)
                        .where(conv.id.eq(conversationId))
                        .fetchFirst()
        );
    }

    private BooleanExpression buildCursorCondition(
            QConversation conv,
            CursorRequestConversationDto request,
            boolean isAsc
    ) {
        if (request.cursor() == null) return null;
        Instant cursorTime = Instant.parse(request.cursor());
        UUID idAfter = request.idAfter() == null ? null : UUID.fromString(request.idAfter());

        if (isAsc) {
            BooleanExpression condition = conv.createdAt.gt(cursorTime);
            if (idAfter != null)
                condition = condition.or(conv.createdAt.eq(cursorTime).and(conv.id.gt(idAfter)));
            return condition;
        } else {
            BooleanExpression condition = conv.createdAt.lt(cursorTime);
            if (idAfter != null)
                condition = condition.or(conv.createdAt.eq(cursorTime).and(conv.id.lt(idAfter)));
            return condition;
        }
    }
}

package org.codeit.sb06.team03.mopl.notification.infra.out;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.notification.domain.Notification;
import org.codeit.sb06.team03.mopl.notification.infra.in.NotificationDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.UUID;

import static org.codeit.sb06.team03.mopl.notification.domain.QNotification.notification;

public interface NotificationRepository extends QuerydslJpaRepository<Notification, UUID> {

    long countByReceiverId(UUID receiverId);

    default Slice<NotificationDto> findAll(CursorGetNotificationsCondition condition) {
        int limit = condition.limit();
        boolean descending = condition.descending();
        var contents = select(Projections.constructor(NotificationDto.class,
                    notification.id,
                    notification.createdAt,
                    notification.receiverId,
                    notification.title,
                    notification.content,
                    notification.level)
                )
                .from(notification)
                .where(
                        receiverIdEq(condition.receiverId()),
                        cursorCondition(condition.cursor(), condition.idAfter(), descending)
                )
                .orderBy(
                        getOrderSpecifier(descending),
                        getTieBreakerOrder(descending)
                )
                .limit(limit + 1)
                .fetch();

        boolean hasNext = false;
        if (contents.size() > limit) {
            contents.remove(limit);
            hasNext = true;
        }

        return new SliceImpl<>(contents, PageRequest.ofSize(limit), hasNext);
    }

    /**
     * Predicates
     */

    private BooleanExpression receiverIdEq(UUID receiverId) {
        return receiverId != null ? notification.receiverId.eq(receiverId) : null;
    }

    private BooleanExpression cursorCondition(String cursor, UUID idAfter, boolean descending) {

        if (!StringUtils.hasText(cursor) || idAfter == null) {
            return null;
        }

        Instant cursorInstant = Instant.parse(cursor);
        if (descending) {
            return notification.createdAt.lt(cursorInstant)
                    .or(notification.createdAt.eq(cursorInstant).and(notification.id.lt(idAfter)));
        } else {
            return notification.createdAt.gt(cursorInstant)
                    .or(notification.createdAt.eq(cursorInstant).and(notification.id.gt(idAfter)));
        }
    }

    /**
     * OrderSpecifiers
     */

    private OrderSpecifier<?> getOrderSpecifier(boolean descending) {
        Order order = descending ? Order.DESC : Order.ASC;
        return new OrderSpecifier<>(order, notification.createdAt);
    }

    private OrderSpecifier<?> getTieBreakerOrder(boolean descending) {
        Order order = descending ? Order.DESC : Order.ASC;
        return new OrderSpecifier<>(order, notification.id);
    }
}

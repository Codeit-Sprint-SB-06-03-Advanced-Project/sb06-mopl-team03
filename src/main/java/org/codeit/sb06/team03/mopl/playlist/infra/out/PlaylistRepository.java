package org.codeit.sb06.team03.mopl.playlist.infra.out;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.codeit.sb06.team03.mopl.account.domain.QAccount.account;
import static org.codeit.sb06.team03.mopl.playlist.domain.entity.QPlaylist.playlist;
import static org.codeit.sb06.team03.mopl.playlist.domain.entity.QSubscription.subscription;

public interface PlaylistRepository extends QuerydslJpaRepository<Playlist, UUID> {

    default Slice<PlaylistReadModel> findAll(
            String keywordLike,
            UUID ownerIdEqual,
            UUID subscriberIdEqual,
            String cursor,
            UUID idAfter,
            int limit,
            String sortDirection,
            String sortBy
    ) {
        Predicate[] predicates = {
                keywordLikePredicate(keywordLike),
                ownerIdEqualPredicate(ownerIdEqual),
                subscriberIdEqualPredicate(subscriberIdEqual),
                cursorExpressionPredicate(cursor, idAfter, sortDirection, sortBy)
        };
        sortDirection = sortDirection.equalsIgnoreCase("ASCENDING") ? "ASC" : "DESC";
        var contents = select(Projections.constructor(PlaylistReadModel.class,
                    playlist.id,
                    playlist.ownerId,
                    playlist.title,
                    playlist.description,
                    playlist.updatedAt,
                    playlist.subscriberCount,
                    playlist.contentCount
                ))
                .from(playlist)
                .leftJoin(subscription).on(subscription.id.playlistId.eq(playlist.id))
                .where(predicates)
                .orderBy(orderByExpressions(sortDirection, sortBy))
                .limit(limit + 1)
                .fetch();

        boolean hasNext = false;
        if (contents.size() > limit) {
            contents.remove(limit);
            hasNext = true;
        }

        return new SliceImpl<>(contents, PageRequest.ofSize(limit), hasNext);
    }

    private static BooleanExpression keywordLikePredicate(String keywordLike){
        if (keywordLike == null || keywordLike.isEmpty()) {
            return null;
        }
        return playlist.title.containsIgnoreCase(keywordLike);
    }

    private static BooleanExpression ownerIdEqualPredicate(UUID ownerId){ // 사용되지 않음
        if (ownerId == null) {
            return null;
        }
        return playlist.ownerId.eq(ownerId);
    }

    private static BooleanExpression subscriberIdEqualPredicate(UUID subscriberId){
        if (subscriberId == null) {
            return null;
        }
        return subscription.id.subscriberId.eq(subscriberId);
    }

    private static BooleanExpression cursorExpressionPredicate(
            String cursor,
            UUID idAfter,
            String sortDirection,
            String sortBy
    ) {
        if (cursor == null || idAfter == null) {
            return null;
        }
        return switch (sortBy) {
            case "subscribeCount" -> {
                final long subscribeCountCursor = Long.parseLong(cursor);
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield playlist.subscriberCount.gt(subscribeCountCursor)
                            .or(playlist.subscriberCount.eq(subscribeCountCursor).and(account.id.goe(idAfter)));
                }
                yield playlist.subscriberCount.lt(subscribeCountCursor)
                        .or(playlist.subscriberCount.eq(subscribeCountCursor).and(account.id.loe(idAfter)));
            }
            default -> {
                final Instant updatedAtCursor = Instant.parse(cursor);
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield playlist.updatedAt.gt(updatedAtCursor)
                            .or(playlist.updatedAt.eq(updatedAtCursor).and(account.id.goe(idAfter)));
                }
                yield playlist.updatedAt.lt(updatedAtCursor)
                        .or(playlist.updatedAt.eq(updatedAtCursor).and(account.id.loe(idAfter)));
            }
        };

    }

    private static OrderSpecifier<?>[] orderByExpressions(String sortDirection, String sortBy) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        orderSpecifiers.add(orderByCursor(sortDirection, sortBy));
        final var orderById = new OrderSpecifier<>(Order.valueOf(sortDirection), playlist.id);
        orderSpecifiers.add(orderById);

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private static OrderSpecifier<?> orderByCursor(String sortDirection, String sortBy) {
        return switch (sortBy) {
            case "subscribeCount" -> new OrderSpecifier<>(Order.valueOf(sortDirection), playlist.subscriberCount);
            default -> new OrderSpecifier<>(Order.valueOf(sortDirection), playlist.updatedAt);
        };
    }
}

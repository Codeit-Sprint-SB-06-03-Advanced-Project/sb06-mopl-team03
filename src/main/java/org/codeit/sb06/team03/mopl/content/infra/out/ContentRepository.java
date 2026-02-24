package org.codeit.sb06.team03.mopl.content.infra.out;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.UserSummary;
import org.codeit.sb06.team03.mopl.content.Content;
import org.codeit.sb06.team03.mopl.content.application.out.WatchingSessionCursorQuery;
import org.codeit.sb06.team03.mopl.content.domain.entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.codeit.sb06.team03.mopl.content.QContent.content;
import static org.codeit.sb06.team03.mopl.content.domain.entity.QReviewStats.reviewStats;
import static org.codeit.sb06.team03.mopl.user.domain.QProfile.profile;
import static org.codeit.sb06.team03.mopl.user.domain.vo.QTimeoutImage.timeoutImage;
import static org.codeit.sb06.team03.mopl.watchingSession.domain.QWatchingSession.watchingSession;

public interface ContentRepository extends QuerydslJpaRepository<Content, UUID> {

    @Query("""
            SELECT c
            FROM Content c
            LEFT JOIN FETCH c.tags
            WHERE c.id = :id
            """)
    Optional<Content> findByIdWithTags(UUID id);

    default List<SessionDetails> findSessionsDetails(WatchingSessionCursorQuery query) {
        OrderSpecifier<Instant> primaryOrder = getPrimaryOrder(query.sortDirection());
        OrderSpecifier<UUID> secondaryOrder = getSecondaryOrder(query.sortDirection());

        BooleanExpression cursorAndAssistanceCursorCondition =
                getCursorAndAssistanceCursorCondition(query.cursor(), query.idAfter(), query.sortDirection());

        BooleanExpression watcherNameLikeCondition = getWatcherNameLikeCondition(query.watcherNameLike());

        List<Tuple> tuples = select(
                watchingSession.id,
                watchingSession.createdAt,
                profile.accountId,
                profile.name,
                timeoutImage.presignedUrl,
                content.id,
                content.type.stringValue(),
                content.title,
                content.description,
                content.thumbnailImage,
                reviewStats.ratingSum,
                reviewStats.reviewCount
        )
                .from(content)
                .innerJoin(content.reviewStats, reviewStats)
                .innerJoin(watchingSession).on(content.id.eq(watchingSession.liveChatId))
                .innerJoin(profile).on(watchingSession.watcherId.eq(profile.accountId))
                .leftJoin(timeoutImage).on(profile.timeoutImage.eq(timeoutImage))
                .where(
                        content.id.eq(query.contentId()),
                        cursorAndAssistanceCursorCondition,
                        watcherNameLikeCondition
                )
                .limit(query.limit() + 1)
                .orderBy(primaryOrder, secondaryOrder)
                .fetch();

        List<String> tags = !tuples.isEmpty() ? getTags(query.contentId()) : List.of();


        return tuples.stream().map(tuple ->
                combineSessionDetails(tuple, tags)).collect(Collectors.toCollection(ArrayList::new));
    }

    default long countByContentIdAndWatcherNameLike(UUID contentId, @Nullable String watcherNameLike) {
        BooleanExpression watcherNameLikeCondition = getWatcherNameLikeCondition(watcherNameLike);

        Long count = select(watchingSession.count())
                .from(watchingSession)
                .innerJoin(profile).on(watchingSession.watcherId.eq(profile.accountId))
                .where(
                        watchingSession.liveChatId.eq(contentId),
                        watcherNameLikeCondition
                )
                .fetchOne();

        return count !=  null ? count : 0;
    }

    private BooleanExpression getWatcherNameLikeCondition(String watcherName) {
        if (watcherName == null) {
            return null;
        }
        return profile.name.like("%" + watcherName + "%");
    }

    private BooleanExpression getCursorAndAssistanceCursorCondition(
            Instant cursor, UUID assistanceCursor, String direction
    ) {
        if (cursor == null || assistanceCursor == null) {
            return null;
        }

        if (direction.equalsIgnoreCase("ASCENDING")) {
            return watchingSession.createdAt.after(cursor).or(
                    watchingSession.createdAt.eq(cursor).and(
                            watchingSession.id.gt(assistanceCursor)
                    )
            );
        } else {
            return watchingSession.createdAt.before(cursor).or(
                    watchingSession.createdAt.eq(cursor).and(
                            watchingSession.id.lt(assistanceCursor)
                    )
            );
        }
    }

    private OrderSpecifier<Instant> getPrimaryOrder(String direction) {
        if (direction.equalsIgnoreCase("ASCENDING")) {
            return watchingSession.createdAt.asc();
        } else {
            return watchingSession.createdAt.desc();
        }
    }

    private OrderSpecifier<UUID> getSecondaryOrder(String direction) {
        if (direction.equalsIgnoreCase("ASCENDING")) {
            return watchingSession.id.asc();
        } else {
            return watchingSession.id.desc();
        }
    }

    private List<String> getTags(UUID contentId) {
        return select(content)
                .from(content)
                .leftJoin(content.tags).fetchJoin()
                .where(content.id.eq(contentId))
                .fetch()
                .stream()
                .flatMap(c -> c.getTags().stream().map(Tag::getName))
                .toList();
    }

    private double calculateReviewAverage(long ratingSum, int reviewCount) {
        if (reviewCount <= 0 || ratingSum <= 0) {
            return 0.0;
        }
        return (double) ratingSum / reviewCount;
    }

    private SessionDetails combineSessionDetails(Tuple tuple, List<String> tags) {
        UUID contentId = tuple.get(content.id);
        long ratingSum = tuple.get(reviewStats.ratingSum);
        int reviewCount = tuple.get(reviewStats.reviewCount);

        return new SessionDetails(
                tuple.get(watchingSession.id),
                tuple.get(watchingSession.createdAt),
                new UserSummary(
                        tuple.get(profile.accountId),
                        tuple.get(profile.name),
                        tuple.get(timeoutImage.presignedUrl)
                ),
                new ContentResult(
                        contentId,
                        tuple.get(content.type.stringValue()),
                        tuple.get(content.title),
                        tuple.get(content.description),
                        tuple.get(content.thumbnailImage),
                        tags,
                        calculateReviewAverage(ratingSum, reviewCount),
                        reviewCount
                )
        );
    }
}

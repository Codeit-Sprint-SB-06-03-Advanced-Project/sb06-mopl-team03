package org.codeit.sb06.team03.mopl.user.infra.out;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.*;

import static org.codeit.sb06.team03.mopl.user.domain.QProfile.profile;

public interface ProfileRepository extends QuerydslJpaRepository<Profile, UUID> {

    List<Profile> findByAccountIdIn(Collection<UUID> accountIds);

    default Optional<UserSummaryDto> getUserSummary(UUID id) {
        return Optional.ofNullable(
                select(userSummaryProjection())
                .from(profile)
                .where(profile.accountId.eq(id))
                .fetchOne()
        );
    }

    default Map<UUID, UserSummaryDto> getUserSummaries(List<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }

        return selectFrom(profile)
                .from(profile)
                .where(profile.accountId.in(ids))
                .transform(GroupBy.groupBy(profile.accountId).as(userSummaryProjection()));
    }

    private FactoryExpression<UserSummaryDto> userSummaryProjection() {
        StringExpression imageUrlPath = new CaseBuilder()
                .when(profile.timeoutImage.isNotNull())
                .then(profile.timeoutImage.presignedUrl)
                .otherwise((String) null);

        return Projections.constructor(UserSummaryDto.class,
                profile.accountId,
                profile.name,
                imageUrlPath
        );
    }


}

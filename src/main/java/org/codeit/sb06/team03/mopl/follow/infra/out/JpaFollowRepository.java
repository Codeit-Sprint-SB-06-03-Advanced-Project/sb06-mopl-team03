package org.codeit.sb06.team03.mopl.follow.infra.out;

import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.follow.domain.Followee;

import java.util.UUID;

public interface JpaFollowRepository extends QuerydslJpaRepository<Followee, UUID> {
}

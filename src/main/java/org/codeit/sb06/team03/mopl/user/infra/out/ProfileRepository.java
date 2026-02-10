package org.codeit.sb06.team03.mopl.user.infra.out;

import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.UUID;

public interface ProfileRepository extends QuerydslJpaRepository<Profile, UUID> {
}

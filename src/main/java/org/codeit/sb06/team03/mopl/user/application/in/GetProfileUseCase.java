package org.codeit.sb06.team03.mopl.user.application.in;

import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * QueryDSL 메서드로 DTO 형태로 한번에 가져올 경우 ProfileNotFoundException 발생
 * -> Profile 객체 자체를 가져오고 애플리케이션 계층에서 가공
 */

public interface GetProfileUseCase {

    Optional<Profile> load(UUID accountId);

    List<Profile> load(List<UUID> accountIds);

    Optional<UserSummaryDto> getUserSummary(UUID id);

    Map<UUID, UserSummaryDto> getUserSummaries(List<UUID> ids);

    Optional<Profile> getDMUserProfile(UUID userId);

}

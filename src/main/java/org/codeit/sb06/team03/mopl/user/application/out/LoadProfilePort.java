package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.domain.Profile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface LoadProfilePort {

    Optional<Profile> load(UUID accountId);

    List<Profile> load(List<UUID> accountIds);

    Optional<UserSummaryDto> getUserSummary(UUID id);

    Map<UUID, UserSummaryDto> getUserSummaries(List<UUID> ids);
}

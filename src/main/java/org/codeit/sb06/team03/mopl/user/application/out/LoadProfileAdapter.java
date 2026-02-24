package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.infra.out.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class LoadProfileAdapter implements LoadProfilePort {

    private final ProfileRepository repository;

    public LoadProfileAdapter(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Profile> load(UUID accountId) {
        return repository.findById(accountId);
    }

    @Override
    public List<Profile> load(List<UUID> accountIds) {
        return repository.findByAccountIdIn(accountIds);
    }

    @Override
    public Optional<UserSummaryDto> getUserSummary(UUID id) {
        return repository.getUserSummary(id);
    }

    @Override
    public Map<UUID, UserSummaryDto> getUserSummaries(List<UUID> ids) {
        return repository.getUserSummaries(ids);
    }
}

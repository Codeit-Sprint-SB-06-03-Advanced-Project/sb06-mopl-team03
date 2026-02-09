package org.codeit.sb06.team03.mopl.user.infra.out;

import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public JpaProfile toJpa(Profile profile) {
        Profile.Snapshot snapshot = profile.snapshot();
        return new JpaProfile()
                .setId(snapshot.accountId())
                .setName(snapshot.name())
                .setVersion(snapshot.version());
    }
}

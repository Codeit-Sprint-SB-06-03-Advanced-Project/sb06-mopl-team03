package org.codeit.sb06.team03.mopl.user.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileService {

    public Profile create(UUID accountId, String name) {
        return Profile.create(accountId, name);
    }
}

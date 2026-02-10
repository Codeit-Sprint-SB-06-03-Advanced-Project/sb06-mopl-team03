package org.codeit.sb06.team03.mopl.user.domain.policy;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BasicProfileImageKeyGenerationPolicy implements ProfileImageKeyGenerationPolicy {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}

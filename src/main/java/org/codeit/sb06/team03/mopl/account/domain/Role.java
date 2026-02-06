package org.codeit.sb06.team03.mopl.account.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN, USER;

    private static final Set<String> names = Arrays.stream(Role.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    public static boolean contains(String name) {
        return names.contains(name);
    }
}
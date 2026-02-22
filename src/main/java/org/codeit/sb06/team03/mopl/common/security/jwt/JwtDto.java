package org.codeit.sb06.team03.mopl.common.security.jwt;

import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;

public record JwtDto(
        UserDto userDto,
        String accessToken
) {
}

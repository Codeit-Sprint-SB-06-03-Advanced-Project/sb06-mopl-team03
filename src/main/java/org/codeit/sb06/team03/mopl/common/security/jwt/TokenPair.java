package org.codeit.sb06.team03.mopl.common.security.jwt;

public record TokenPair(
        String refreshToken,
        String accessToken
) {
}

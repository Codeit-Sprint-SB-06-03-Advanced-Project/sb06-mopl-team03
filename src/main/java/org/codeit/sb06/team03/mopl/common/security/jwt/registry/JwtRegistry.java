package org.codeit.sb06.team03.mopl.common.security.jwt.registry;

import org.codeit.sb06.team03.mopl.common.security.jwt.JwtClaims;
import org.codeit.sb06.team03.mopl.common.security.jwt.TokenPair;

public interface JwtRegistry {

    TokenPair register(JwtClaims jwtClaims);

    boolean hasActiveAccessToken(String accessToken);

    boolean hasActiveRefreshToken(String refreshToken);

    void invalidateAll(JwtClaims jwtClaims);

    void invalidateToken(String refreshToken);

    TokenPair rotate(String oldRefreshToken);

    void clearExpiredTokenSession();
}

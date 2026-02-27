package org.codeit.sb06.team03.mopl.common.security.jwt.registry;

import org.codeit.sb06.team03.mopl.common.security.jwt.*;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class PersistentJwtRegistry implements JwtRegistry {

    private final TokenSessionRepository tokenSessionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final int MAX_SESSION;

    public PersistentJwtRegistry(
            @Value("${mopl.jwt.max-session}") int maxSession,
            TokenSessionRepository tokenSessionRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.MAX_SESSION = maxSession;
        this.tokenSessionRepository = tokenSessionRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenPair register(JwtClaims jwtClaims) {
        TokenResult refreshToken = jwtTokenProvider.generateRefreshToken(jwtClaims);
        TokenResult accessToken = jwtTokenProvider.generateAccessToken(jwtClaims);

        TokenSession session = TokenSession.create(
                refreshToken.id(),
                accessToken.id(),
                refreshToken.expiresAt(),
                accessToken.expiresAt(),
                jwtClaims.id()
        );

        List<TokenSession> sessions =
                tokenSessionRepository.findByAccountIdOrderByRefreshTokenExpiresAtAsc(jwtClaims.id());
        int count = sessions.size();

        if (count >= MAX_SESSION) {
            TokenSession expireSession = sessions.getFirst();
            tokenSessionRepository.delete(expireSession);
        }

        tokenSessionRepository.save(session);

        return new TokenPair(refreshToken.token(), accessToken.token());
    }

    @Override
    public boolean hasActiveAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateAccessToken(accessToken)) {
            return false;
        }
        UUID accessTokenId = jwtTokenProvider.getTokenId(accessToken);
        return tokenSessionRepository.existsByAccessTokenId(accessTokenId);
    }

    @Override
    public boolean hasActiveRefreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            return false;
        }
        UUID refreshTokenId = jwtTokenProvider.getTokenId(refreshToken);
        return tokenSessionRepository.existsById(refreshTokenId);
    }

    @Override
    public void invalidateAll(JwtClaims jwtClaims) {
        tokenSessionRepository.deleteByAccountId(jwtClaims.id());
    }

    @Override
    public void invalidateToken(String refreshToken) {
        tokenSessionRepository.deleteById(jwtTokenProvider.getTokenId(refreshToken));
    }

    @Override
    public TokenPair rotate(String oldRefreshToken) {
        if (!hasActiveRefreshToken(oldRefreshToken)) {
            throw new InvalidTokenException();
        }

        invalidateToken(oldRefreshToken);
        JwtClaims jwtClaims = jwtTokenProvider.getClaims(oldRefreshToken);
        return register(jwtClaims);
    }

    @Override
    @Transactional // 없으면 에러 발생
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void clearExpiredTokenSession() {
        tokenSessionRepository.deleteExpiredTokenSessions();
    }
}
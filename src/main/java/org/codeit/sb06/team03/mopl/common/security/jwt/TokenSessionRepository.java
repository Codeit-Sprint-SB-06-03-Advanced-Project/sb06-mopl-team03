package org.codeit.sb06.team03.mopl.common.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TokenSessionRepository extends JpaRepository<TokenSession, UUID> {

    List<TokenSession> findByAccountIdOrderByRefreshTokenExpiresAtAsc(UUID accountId);

    void deleteByAccountId(UUID accountId);

    boolean existsByAccessTokenId(UUID accessTokenId);

    @Modifying
    @Query(value = """
            DELETE FROM TokenSession t
            WHERE t.refreshTokenExpiresAt < CURRENT_TIMESTAMP
            """)
    void deleteExpiredTokenSessions();
}
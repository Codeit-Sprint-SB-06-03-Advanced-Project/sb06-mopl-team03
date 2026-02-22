package org.codeit.sb06.team03.mopl.common.security.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "token_sessions")
public class TokenSession {

    @Id
    private UUID refreshTokenId;

    @Column(nullable = false)
    private UUID accessTokenId;

    @Column(nullable = false)
    private Instant refreshTokenExpiresAt;

    @Column(nullable = false)
    private Instant accessTokenExpiresAt;

    @Column(nullable = false)
    private UUID accountId;

    public static TokenSession create(UUID refreshTokenId, UUID accessTokenId, Instant refreshTokenExpiresAt, Instant accessTokenExpiresAt, UUID accountId) {
        TokenSession tokenSession = new TokenSession();
        tokenSession.refreshTokenId = refreshTokenId;
        tokenSession.accessTokenId = accessTokenId;
        tokenSession.refreshTokenExpiresAt = refreshTokenExpiresAt;
        tokenSession.accessTokenExpiresAt = accessTokenExpiresAt;
        tokenSession.accountId = accountId;
        return tokenSession;
    }
}

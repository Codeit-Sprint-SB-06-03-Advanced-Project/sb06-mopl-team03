package org.codeit.sb06.team03.mopl.common.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.InvalidTokenException;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.TokenGenerationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    private final int accessTokenExpirationMs;
    private final JWSSigner accessTokenSigner;
    private final JWSVerifier accessTokenVerifier;

    private final int refreshTokenExpirationMs;
    private final JWSSigner refreshTokenSigner;
    private final JWSVerifier refreshTokenVerifier;

    public JwtTokenProvider(
            @Value("${mopl.jwt.access-token.expiration-ms}")
            int accessTokenExpirationMs,
            @Value("${mopl.jwt.refresh-token.expiration-ms}")
            int refreshTokenExpirationMs,
            @Value("${mopl.jwt.access-token.secret}")
            String accessTokenSecret,
            @Value("${mopl.jwt.refresh-token.secret}")
            String refreshTokenSecret
    ) throws JOSEException {
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;

        byte[] accessTokenSecretBytes = accessTokenSecret.getBytes();
        this.accessTokenSigner = new MACSigner(accessTokenSecretBytes);
        this.accessTokenVerifier = new MACVerifier(accessTokenSecretBytes);

        byte[] refreshTokenSecretBytes = refreshTokenSecret.getBytes();
        this.refreshTokenSigner = new MACSigner(refreshTokenSecretBytes);
        this.refreshTokenVerifier = new MACVerifier(refreshTokenSecretBytes);
    }

    public TokenResult generateAccessToken(org.codeit.sb06.team03.mopl.common.security.jwt.JwtClaims jwtClaims) {
        return generateToken(jwtClaims, accessTokenExpirationMs, accessTokenSigner, JwtTokenType.ACCESS);
    }

    public TokenResult generateRefreshToken(org.codeit.sb06.team03.mopl.common.security.jwt.JwtClaims jwtClaims) {
        return generateToken(jwtClaims, refreshTokenExpirationMs, refreshTokenSigner, JwtTokenType.REFRESH);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessTokenVerifier, JwtTokenType.ACCESS);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshTokenVerifier, JwtTokenType.REFRESH);
    }

    public UUID getTokenId(String token) {
        JWTClaimsSet claimsSet = parseClaims(token);
        return UUID.fromString(claimsSet.getJWTID());
    }

    public Instant getExpiresAt(String token) {
        JWTClaimsSet claimsSet = parseClaims(token);
        return claimsSet.getExpirationTime().toInstant();
    }

    public JwtClaims getClaims(String token) {
        JWTClaimsSet claimsSet = parseClaims(token);
        Map<String, Object> claims = claimsSet.getClaims();

        UUID accountId = UUID.fromString(claimsSet.getSubject());
        String email = claims.get(JwtClaimNames.EMAIL).toString();
        String name = claims.get(JwtClaimNames.NAME).toString();
        Object profileImageUrlObject = claims.getOrDefault(JwtClaimNames.PROFILE_IMAGE_URL, null);
        String profileImageUrl = profileImageUrlObject != null ? profileImageUrlObject.toString() : null;
        String role = claims.get(JwtClaimNames.ROLE).toString();

        return new JwtClaims(
                accountId, email, name, profileImageUrl, role
        );
    }

    private TokenResult generateToken(
            JwtClaims jwtClaims,
            int expirationMs,
            JWSSigner signer,
            String type
    ) {
        try {
            UUID tokenUuid = UUID.randomUUID();
            String tokenId = tokenUuid.toString();

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationMs);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .expirationTime(expiryDate)
                    .subject(jwtClaims.id().toString())
                    .jwtID(tokenId)
                    .claim(JwtClaimNames.TYPE, type)
                    .claim(JwtClaimNames.ROLE, jwtClaims.role())
                    .claim(JwtClaimNames.EMAIL, jwtClaims.email())
                    .claim(JwtClaimNames.NAME, jwtClaims.name())
                    .claim(JwtClaimNames.PROFILE_IMAGE_URL, jwtClaims.profileImageUrl())
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );

            signedJWT.sign(signer);
            String token = signedJWT.serialize();

            log.debug("Generated token for user: {}", jwtClaims.email());
            return new TokenResult(token, tokenUuid, expiryDate.toInstant());
        } catch (JOSEException e) {
            log.debug("JWT token generation failed");
            throw new TokenGenerationFailedException();
        }
    }

    private boolean validateToken(String token, JWSVerifier verifier, String type) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.verify(verifier)) {
                log.debug("JWT signature verification failed");
                return false;
            }

            String tokenType = signedJWT.getJWTClaimsSet().getClaim(JwtClaimNames.TYPE).toString();
            if (!tokenType.equals(type)) {
                log.debug("JWT token type mismatch: expected {}, got {}", type, tokenType);
                return false;
            }

            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expiration == null || expiration.before(new Date())) {
                log.debug("JWT token expired");
                return false;
            }

            return true;
        } catch (Exception e) {
            log.debug("JWT token validation failed");
            return false;
        }
    }

    private JWTClaimsSet parseClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }
}

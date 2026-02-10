package org.codeit.sb06.team03.mopl.common.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

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
            @Value("${mopl.jwt.access-token.expiration-in-minutes}")
            int accessTokenExpirationMs,
            @Value("${mopl.jwt.refresh-token.expiration-in-minutes}")
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

    public String generateAccessToken(MoplUserDetails userDetails) throws JOSEException {
        return generateToken(userDetails, accessTokenExpirationMs, accessTokenSigner);
    }

    public String generateRefreshToken(MoplUserDetails userDetails) throws JOSEException {
        return generateToken(userDetails, refreshTokenExpirationMs, refreshTokenSigner);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessTokenVerifier);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshTokenVerifier);
    }

    public String getAccountId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }

    public String getRole(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getClaim(JwtCustomClaims.ROLE).toString();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }

    public String getEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getClaim(JwtCustomClaims.EMAIL).toString();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }

    public String getName(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getClaim(JwtCustomClaims.NAME).toString();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }

    public String getProfileImageUrl(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getClaim(JwtCustomClaims.PROFILE_IMAGE_URL).toString();
        } catch (ParseException e) {
            log.debug("Invalid JWT Token");
            throw new InvalidTokenException();
        }
    }

    private String generateToken(MoplUserDetails userDetails, int expirationInMinutes, JWSSigner signer)
            throws JOSEException {
        UserDto userDto = userDetails.getUserDto();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMinutes);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .expirationTime(expiryDate)
                .subject(userDto.id().toString())
                .claim(JwtCustomClaims.ROLE, userDto.role())
                .claim(JwtCustomClaims.EMAIL, userDto.email())
                .claim(JwtCustomClaims.NAME, userDto.name())
                .claim(JwtCustomClaims.PROFILE_IMAGE_URL, userDto.profileImageUrl())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        signedJWT.sign(signer);
        String token = signedJWT.serialize();

        log.debug("Generated token for user: {}", userDto.email());
        return token;
    }

    private boolean validateToken(String token, JWSVerifier verifier) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.verify(verifier)) {
                log.debug("JWT signature verification failed");
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
}

package org.codeit.sb06.team03.mopl.common.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RefreshTokenCookieProvider {

    private final int maxAge;
    private final String REFRESH_TOKEN_COOKIE_NAME = "REFRESH_TOKEN";

    public RefreshTokenCookieProvider(
            @Value("${mopl.jwt.refresh-token.expiration-ms}") int refreshTokenExpirationMs
    ) {
        this.maxAge = refreshTokenExpirationMs / 1000;
    }

    public Cookie generateRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setMaxAge(maxAge);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        return refreshTokenCookie;
    }

    public Cookie generateRefreshTokenExpirationCookie() {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, "");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        return refreshTokenCookie;
    }

    public Cookie resolveCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .findFirst()
                .orElse(null);
    }
}

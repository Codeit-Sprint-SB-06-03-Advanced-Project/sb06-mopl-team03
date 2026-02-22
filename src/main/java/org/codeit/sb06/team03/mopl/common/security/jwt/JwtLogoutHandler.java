package org.codeit.sb06.team03.mopl.common.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.jwt.registry.JwtRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtRegistry jwtRegistry;
    private final RefreshTokenCookieProvider cookieProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie refreshTokenCookie = cookieProvider.generateRefreshTokenExpirationCookie();
        response.addCookie(refreshTokenCookie);

        Cookie oldRefreshTokenCookie = cookieProvider.resolveCookie(request);
        if (oldRefreshTokenCookie != null) {
            jwtRegistry.invalidateToken(oldRefreshTokenCookie.getValue());
        }
    }
}
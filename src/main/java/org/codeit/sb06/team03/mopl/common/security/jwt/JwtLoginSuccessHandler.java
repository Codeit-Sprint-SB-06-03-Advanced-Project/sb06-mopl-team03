package org.codeit.sb06.team03.mopl.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.TokenGenerationFailedException;
import org.codeit.sb06.team03.mopl.common.security.jwt.registry.JwtRegistry;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtRegistry jwtRegistry;
    private final ObjectMapper objectMapper;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (authentication.getPrincipal() instanceof MoplUserDetails userDetails) {
            try {
                JwtClaims jwtClaims = userDetailsToJwtClaims(userDetails);
                TokenPair tokenPair = jwtRegistry.register(jwtClaims);

                Cookie refreshTokenCookie =
                        refreshTokenCookieProvider.generateRefreshTokenCookie(tokenPair.refreshToken());

                response.addCookie(refreshTokenCookie);
                response.setStatus(HttpServletResponse.SC_OK);

                JwtDto jwtDto = new JwtDto(userDetails.getUserDto(), tokenPair.accessToken());
                objectMapper.writeValue(response.getWriter(), jwtDto);
            } catch (TokenGenerationFailedException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                ErrorResponse errorResponse = new ErrorResponse(
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        List.of()
                );
                objectMapper.writeValue(response.getWriter(), errorResponse);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            ErrorResponse errorResponse = new ErrorResponse(
                    AuthenticationException.class.getSimpleName(),
                    "유효하지 않은 User Details 입니다.",
                    List.of()
            );
            objectMapper.writeValue(response.getWriter(), errorResponse);
        }
    }

    private JwtClaims userDetailsToJwtClaims(MoplUserDetails moplUserDetails) {
        UserDto userDto = moplUserDetails.getUserDto();
        return new JwtClaims(
                userDto.id(),
                userDto.email(),
                userDto.name(),
                userDto.profileImageUrl(),
                userDto.role()
        );
    }
}

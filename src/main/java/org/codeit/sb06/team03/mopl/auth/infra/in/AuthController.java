package org.codeit.sb06.team03.mopl.auth.infra.in;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffAuthService;
import org.codeit.sb06.team03.mopl.common.security.jwt.*;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.InvalidTokenException;
import org.codeit.sb06.team03.mopl.common.security.jwt.registry.JwtRegistry;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi{

    private final BffAuthService bffAuthService;
    private final RefreshTokenCookieProvider cookieProvider;
    private final JwtRegistry jwtRegistry;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        bffAuthService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping("/csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie oldRefreshTokenCookie =  cookieProvider.resolveCookie(request);
        if (oldRefreshTokenCookie == null) {
            throw new InvalidTokenException();
        }

        TokenPair tokenPair = jwtRegistry.rotate(oldRefreshTokenCookie.getValue());

        Cookie newRefreshTokenCookie = cookieProvider.generateRefreshTokenCookie(tokenPair.refreshToken());
        response.addCookie(newRefreshTokenCookie);

        JwtClaims jwtClaims = jwtTokenProvider.getClaims(tokenPair.refreshToken());
        String accountId = jwtClaims.id().toString();
        UserDto userDto = bffAuthService.getUserDto(accountId);
        JwtDto jwtDto = new JwtDto(
                userDto, tokenPair.accessToken()
        );

        return ResponseEntity.ok(jwtDto);
    }

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<JwtDto> login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PostMapping("/sign-out")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
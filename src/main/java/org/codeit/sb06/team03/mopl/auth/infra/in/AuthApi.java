package org.codeit.sb06.team03.mopl.auth.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;

@Tag(name = "인증 관리")
public interface AuthApi {
    @Operation(summary = "비밀번호 초기화")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "404", description = "해당 리소스 없음")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> resetPassword(@RequestBody(required = true) @Valid ResetPasswordRequest request);

    @Operation(summary = "CSRF 토큰 조회")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> getCsrfToken(@Parameter(hidden = true) CsrfToken csrfToken);

    @Operation(summary = "refresh token 재발급")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<JwtDto> refresh(HttpServletRequest request, HttpServletResponse response);

    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<JwtDto> login(@Parameter(description = "email 입니다.") String username, String password);

    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> logout();
}

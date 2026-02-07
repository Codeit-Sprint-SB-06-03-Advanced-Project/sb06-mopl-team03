package org.codeit.sb06.team03.mopl.auth.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증 관리")
public interface AuthApi {
    @Operation(summary = "비밀번호 초기화")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "404", description = "해당 리소스 없음")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> resetPassword(@RequestBody(required = true) @Valid ResetPasswordRequest request);
}

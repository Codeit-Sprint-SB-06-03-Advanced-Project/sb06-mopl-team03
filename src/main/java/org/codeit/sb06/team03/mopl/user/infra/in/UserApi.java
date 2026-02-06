package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "사용자 관리")
public interface UserApi {

    @Operation(summary = "사용자 등록(회원가입)")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<UserDto> postUsers(@RequestBody(required = true) @Valid UserCreateRequest request);

    @Operation(summary = "사용자 비밀번호 수정")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> updatePassword(
            @PathVariable String userId,
            @RequestBody(required = true) @Valid PasswordUpdateRequest request
    );

    @Operation(summary = "[어드민] 권한 수정")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "403", description = "권한 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> patchUsersRole(String userId, @RequestBody(required = true) @Valid UserRoleUpdateRequest request);

    @Operation(summary = "[어드민] 사용자 목록 조회 (커서 페이지네이션)")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "403", description = "권한 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<CursorResponseUserDto> getUsers(@ParameterObject @Valid CursorRequestUserDto request);
}

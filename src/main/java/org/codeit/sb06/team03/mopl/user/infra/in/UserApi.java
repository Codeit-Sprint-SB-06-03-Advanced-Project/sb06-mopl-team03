package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.hibernate.validator.constraints.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사용자 관리")
@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "401", description = "인증 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "403", description = "권한 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
public interface UserApi {

    @Operation(summary = "사용자 등록(회원가입)")
    @ApiResponse(responseCode = "201", description = "성공")
    ResponseEntity<UserDto> postUsers(@RequestBody(required = true) @Valid UserCreateRequest request);

    @Operation(summary = "사용자 비밀번호 수정")
    ResponseEntity<Void> updatePassword(
            @PathVariable String userId,
            @RequestBody(required = true) @Valid PasswordUpdateRequest request
    );

    @Operation(summary = "[어드민] 권한 수정")
    @ApiResponse(responseCode = "204", description = "성공")
    ResponseEntity<Void> patchUsersRole(
            @UUID(message = "잘못된 UUID 형식입니다.") String userId,
            @RequestBody(required = true) UserRoleUpdateRequest request
    );

    @Operation(summary = "[어드민] 계정 잠금 상태 변경")
    @ApiResponse(responseCode = "204", description = "성공")
    ResponseEntity<Void> patchUsersLockStatus(
            @UUID(message = "잘못된 UUID 형식입니다.") String userId,
            @RequestBody(required = true) UserLockUpdateRequest request
    );

    @Operation(summary = "[어드민] 사용자 목록 조회 (커서 페이지네이션)")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<CursorResponseUserDto> getUsers(@ParameterObject @Valid CursorRequestUserDto request);

    @Operation(summary = "사용자 상세 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "해당 리소스 없음")
    ResponseEntity<UserDto> getUsersById(@UUID String userId);

    @Operation(summary = "프로필 변경", description = "본인의 프로필만 변경할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<UserDto> patchUserProfile(
            @UUID String userId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @Valid UserUpdateRequest request,
            @Nullable MultipartFile image
    );

    @Operation(summary = "특정 사용자의 시청 세션 조회 (nullable)")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<SessionDetails> getSessionDetails (
            @UUID(message = "잘못된 UUID 형식입니다.")
            String WatcherId,
            MoplUserDetails userDetails
    );
}

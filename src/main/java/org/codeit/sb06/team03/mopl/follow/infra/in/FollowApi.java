package org.codeit.sb06.team03.mopl.follow.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "팔로우 관리")
@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "401", description = "인증 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "403", description = "권한 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
public interface FollowApi {

    @Operation(summary = "팔로우")
    @ApiResponse(responseCode = "201", description = "성공")
    ResponseEntity<FollowDto> postFollows(@Valid FollowRequest request, MoplUserDetails userDetails);

    @Operation(summary = "특정 유저를 내가 팔로우하는지 여부 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<Boolean> getFollowsFollowedByMe(@Parameter(schema = @Schema(format = "uuid")) @UUID String followeeId, MoplUserDetails userDetails);

    @Operation(summary = "특정 유저의 팔로워 수 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<Long> getFollowersCount(@Parameter(schema = @Schema(format = "uuid")) @UUID String followeeId);

    @Operation(summary = "팔로우 취소", description = "API 요청자 본인의 팔로우만 취소할 수 있습니다.")
    @ApiResponse(responseCode = "204", description = "성공")
    ResponseEntity<Void> deleteFollows(@Parameter(schema = @Schema(format = "uuid")) @UUID String followId, MoplUserDetails userDetails);
}

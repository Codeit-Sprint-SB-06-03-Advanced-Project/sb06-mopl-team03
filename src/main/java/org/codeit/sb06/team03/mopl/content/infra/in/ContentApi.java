package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.content.application.in.CursorResponseWatchingSessionDto;
import org.hibernate.validator.constraints.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

public interface ContentApi {

    @Operation(summary = "특정 콘텐츠의 시청 세션 목록 조회 (커서 페이지네이션)")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<CursorResponseWatchingSessionDto> getWatchingSessions (
            @UUID(message = "잘못된 UUID 형식입니다.")
            String contentId,
            @Valid
            @ParameterObject
            WatchingSessionCursorRequest watchingSessionCursorRequest
    );
}

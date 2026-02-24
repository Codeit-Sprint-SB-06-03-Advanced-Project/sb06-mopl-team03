package org.codeit.sb06.team03.mopl.playlist.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

@Tag(name = "플레이리스트 관리")
public interface PlaylistApi {

    @Operation(summary = "플레이리스트 생성")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<PlaylistDto> postPlaylist(
            PlaylistCreateRequest request,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 목록 조회 (커서 페이지네이션)")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<CursorResponsePlaylistDto> getPlaylists(
            @ParameterObject CursorRequestPlaylistDto request,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 단건 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<PlaylistDto> getPlaylist(
            String playlistId,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 수정")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<PlaylistDto> patchPlaylist(
            String playlistId,
            PlaylistUpdateRequest request,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 삭제")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> deletePlaylist(
            String playlistId,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트에 컨텐츠 추가")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> postCuration(
            String playlistId,
            String contentId,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트에서 컨텐츠 삭제")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> deleteCuration(
            String playlistId,
            String contentId,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 구독")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> postSubscription(
            String playlistId,
            MoplUserDetails user
    );

    @Operation(summary = "플레이리스트 구독 취소")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 오류")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    ResponseEntity<Void> deleteSubscription(
            String playlistId,
            MoplUserDetails user
    );

}

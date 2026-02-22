package org.codeit.sb06.team03.mopl.playlist.infra.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffPlaylistService;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController implements PlaylistApi {

    private final BffPlaylistService bffPlaylistService;

    @Override
    @PostMapping
    public ResponseEntity<PlaylistDto> postPlaylist(
            @RequestBody @Valid PlaylistCreateRequest request,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        PlaylistDto playlistDto = bffPlaylistService.createPlaylist(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistDto);
    }

    @Override
    @GetMapping
    public ResponseEntity<CursorResponsePlaylistDto> getPlaylists(
            @ModelAttribute CursorRequestPlaylistDto request
    ) {
        CursorResponsePlaylistDto response = bffPlaylistService.getPlaylists(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDto> getPlaylist(
            @PathVariable String playlistId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        PlaylistDto playlistDto = bffPlaylistService.getPlaylist(playlistId, user.getId()); // 조회자 ID
        return ResponseEntity.ok(playlistDto);
    }

    @Override
    @PatchMapping("/{playlistId}")
    public ResponseEntity<PlaylistDto> patchPlaylist(
            @PathVariable String playlistId,
            @RequestBody PlaylistUpdateRequest request,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        PlaylistDto playlistDto = bffPlaylistService.updatePlayList(playlistId, request, user.getId());
        return ResponseEntity.ok(playlistDto);
    }

    @Override
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(
            @PathVariable String playlistId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffPlaylistService.deletePlaylist(playlistId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{playlistId}/contents/{contentId}")
    public ResponseEntity<Void> postCuration(
            @PathVariable String playlistId,
            @PathVariable String contentId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffPlaylistService.addContentToPlaylist(playlistId, contentId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{playlistId}/contents/{contentId}")
    public ResponseEntity<Void> deleteCuration(
            @PathVariable String playlistId,
            @PathVariable String contentId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffPlaylistService.deleteContentFromPlaylist(playlistId, contentId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{playlistId}/subscription")
    public ResponseEntity<Void> postSubscription(
            @PathVariable String playlistId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffPlaylistService.subscribePlaylist(playlistId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{playlistId}/subscription")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable String playlistId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffPlaylistService.unsubscribePlaylist(playlistId, user.getId());
        return ResponseEntity.noContent().build();
    }
}

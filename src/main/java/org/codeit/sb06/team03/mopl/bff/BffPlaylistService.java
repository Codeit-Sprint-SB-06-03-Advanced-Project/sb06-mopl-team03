package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;

import java.util.UUID;

public interface BffPlaylistService {

    PlaylistDto createPlaylist(PlaylistCreateRequest request, UUID ownerId);

    CursorResponsePlaylistDto getPlaylists(CursorRequestPlaylistDto request);

    PlaylistDto getPlaylist(String playlistId, UUID viewerId);

    PlaylistDto updatePlayList(String playlistId, PlaylistUpdateRequest request, UUID ownerId);

    void deletePlaylist(String playlistId, UUID ownerId);

    void addContentToPlaylist(String playlistId, String contentId, UUID ownerId);

    void deleteContentFromPlaylist(String playlistId, String contentId, UUID ownerId);

    void subscribePlaylist(String playlistId, UUID accountId);

    void unsubscribePlaylist(String playlistId, UUID accountId);
}

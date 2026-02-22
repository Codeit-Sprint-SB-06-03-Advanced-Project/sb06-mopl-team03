package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.playlist.application.in.*;
import org.codeit.sb06.team03.mopl.playlist.infra.in.*;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffPlaylistService implements BffPlaylistService {

    private final PlaylistMapper playlistMapper;
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final GetPlaylistsUseCase getPlaylistsUseCase;
    private final GetSinglePlaylistUseCase getPlaylistUseCase;
    private final UpdatePlaylistUseCase updatePlaylistUseCase;
    private final DeletePlaylistUseCase deletePlaylistUseCase;
    private final AddContentToCurationUseCase addContentToCurationUseCase;
    private final DeleteContentFromCurationUseCase deleteContentFromCurationUseCase;
    private final SubscribePlaylistUseCase subscribePlaylistUseCase;
    private final UnsubscribePlaylistUseCase unsubscribePlaylistUseCase;

    @Override
    public PlaylistDto createPlaylist(PlaylistCreateRequest request, UUID ownerId) {
        CreatePlaylistCommand command = playlistMapper.toCommand(request);
        return createPlaylistUseCase.create(command, ownerId);
    }

    @Override
    public CursorResponsePlaylistDto getPlaylists(CursorRequestPlaylistDto request) {
        return getPlaylistsUseCase.get(request);
    }

    @Override
    public PlaylistDto getPlaylist(String playlistId, UUID viewerId) {
        return getPlaylistUseCase.get(playlistId, viewerId);
    }

    @Override
    public PlaylistDto updatePlayList(String playlistId, PlaylistUpdateRequest request, UUID ownerId) {
        UpdatePlaylistCommand command = playlistMapper.toCommand(request);
        return updatePlaylistUseCase.update(playlistId, command, ownerId);
    }

    @Override
    public void deletePlaylist(String playlistId, UUID ownerId) {
        deletePlaylistUseCase.delete(playlistId, ownerId);
    }

    @Override
    public void addContentToPlaylist(String playlistId, String contentId, UUID ownerId) {
        addContentToCurationUseCase.addContentToPlaylist(playlistId, contentId, ownerId);
    }

    @Override
    public void deleteContentFromPlaylist(String playlistId, String contentId, UUID ownerId) {
        deleteContentFromCurationUseCase.deleteContentFromPlaylist(playlistId, contentId, ownerId);
    }

    @Override
    public void subscribePlaylist(String playlistId, UUID userId) {
        subscribePlaylistUseCase.subscribe(playlistId, userId);
    }

    @Override
    public void unsubscribePlaylist(String playlistId, UUID userId) {
        unsubscribePlaylistUseCase.unsubscribe(playlistId, userId);
    }
}

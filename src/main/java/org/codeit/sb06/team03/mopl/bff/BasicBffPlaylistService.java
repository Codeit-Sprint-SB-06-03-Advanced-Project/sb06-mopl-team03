package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.enums.SortDirection;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.application.in.*;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.codeit.sb06.team03.mopl.playlist.infra.in.*;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistCreateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.PlaylistUpdateRequest;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.application.in.GetProfileUseCase;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.exception.ProfileNotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private final GetProfileUseCase getProfileUseCase;
    private final GetSubscriptionUseCase getSubscriptionUseCase;
//    private final GetContentUseCase getContentUseCase;

    @Override
    public PlaylistDto createPlaylist(PlaylistCreateRequest request, UUID ownerId) {

        CreatePlaylistCommand command = playlistMapper.toCommand(request);
        Playlist playlist = createPlaylistUseCase.create(command, ownerId);

        MoplUserDetails userDetails = (MoplUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        UserSummaryDto owner = new UserSummaryDto(
                userDetails.getId(),
                userDetails.getUserDto().name(),
                userDetails.getUserDto().profileImageUrl());

        return PlaylistDto.toDto(playlist, owner, false , Collections.emptyList()); // TODO
    }

    @Override
    public CursorResponsePlaylistDto getPlaylists(CursorRequestPlaylistDto request, UUID viewerId) {
        Slice<PlaylistReadModel> slice = getPlaylistsUseCase.get(request, viewerId);
        List<PlaylistReadModel> readModels = slice.getContent();

        List<UUID> ownerIds = readModels.stream().map(PlaylistReadModel::ownerId).toList();
        List<Profile> profileList = getProfileUseCase.load(ownerIds);
        Map<UUID, UserSummaryDto> owners = profileList.stream()
                .collect(Collectors.toMap(
                        Profile::getAccountId,
                        UserSummaryDto::from,
                        (existing, replacement) -> existing // 중복 ID 발생 시 기존 값 유지
                ));

        List<UUID> playlistIds = readModels.stream().map(PlaylistReadModel::id).toList();
        Map<UUID, Boolean> subscribedByMe = getSubscriptionUseCase.isSubscribed(playlistIds, viewerId);

        List<PlaylistDto> data = readModels.stream()
                .map(readModel -> PlaylistDto.toDto(
                        readModel,
                        owners.get(readModel.ownerId()),
                        subscribedByMe.getOrDefault(readModel.id(), false),
                        Collections.emptyList() // TODO
                ))
                .toList();

        String nextCursor = null;
        UUID nextIdAfter = null;
        if (slice.hasNext()) {
            PlaylistReadModel lastItem = readModels.getLast();
            nextCursor = lastItem.updatedAt().toString();
            nextIdAfter = lastItem.id();
        }

        return new CursorResponsePlaylistDto(
                data,
                nextCursor,
                nextIdAfter,
                slice.hasNext(),
                0, // TODO
                request.sortBy(),
                SortDirection.valueOf(request.sortDirection())
        );
    }

    @Override
    public PlaylistDto getPlaylist(String playlistId, UUID viewerId) {
        PlaylistReadModel readModel = getPlaylistUseCase.get(playlistId, viewerId);

        Profile profile = getProfileUseCase.load(readModel.ownerId())
                .orElseThrow(() -> new ProfileNotFoundException(readModel.ownerId()));
        UserSummaryDto owner = UserSummaryDto.from(profile);

        boolean subscribedByMe = getSubscriptionUseCase.isSubscribed(playlistId, viewerId);
        // TODO contents
        return PlaylistDto.toDto(readModel, owner, subscribedByMe, Collections.emptyList());
    }

    @Override
    public PlaylistDto updatePlayList(String playlistId, PlaylistUpdateRequest request, UUID ownerId) {

        UpdatePlaylistCommand command = playlistMapper.toCommand(request);
        Playlist playlist = updatePlaylistUseCase.update(playlistId, command, ownerId);

        MoplUserDetails userDetails = (MoplUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        UserSummaryDto owner = new UserSummaryDto(
                userDetails.getId(),
                userDetails.getUserDto().name(),
                userDetails.getUserDto().profileImageUrl());
        // TODO contents
        return PlaylistDto.toDto(playlist, owner, false , Collections.emptyList());
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

package org.codeit.sb06.team03.mopl.playlist.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidIdentifierException;
import org.codeit.sb06.team03.mopl.playlist.application.in.GetSinglePlaylistUseCase;
import org.codeit.sb06.team03.mopl.playlist.application.in.GetPlaylistsUseCase;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadCurationPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSinglePlaylistPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadPlaylistsPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;
import org.codeit.sb06.team03.mopl.playlist.domain.exception.PlaylistNotFoundException;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.CursorResponsePlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.application.out.LoadProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.exception.ProfileNotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaylistQueryService implements GetPlaylistsUseCase, GetSinglePlaylistUseCase {

    private final LoadPlaylistsPort loadPlaylistsPort;
    private final LoadSinglePlaylistPort loadSinglePlaylistPort;
    private final LoadProfilePort loadProfilePort;
//    private final LoadContentPort loadContentPort;
    private final LoadCurationPort loadCurationPort;
    private final LoadSubscriptionPort loadSubscriptionPort;

    @Override
    public CursorResponsePlaylistDto get(CursorRequestPlaylistDto request) {

        final String keywordLike = request.keywordLike();
        final UUID ownerIdEqual = parseUUID(request.ownerIdEqual());
        final UUID subscriberIdEqual = parseUUID(request.subscriberIdEqual());
        final String cursor = StringUtils.hasText(request.cursor()) ? request.cursor() : null;
        final UUID idAfter = parseUUID(request.idAfter());
        final int limit = request.limit();
        final String sortDirection = request.sortDirection();
        final String sortBy = request.sortBy();

        Slice<Playlist> playlists = loadPlaylistsPort.findAll(
                keywordLike,
                ownerIdEqual,
                subscriberIdEqual,
                cursor,
                idAfter,
                limit,
                sortDirection,
                sortBy
        );
        List<UUID> playlistIds = playlists.getContent().stream().map(Playlist::getId).toList();
        Map<UUID, List<UUID>> contentIds = loadCurationPort.findAllByPlaylistIdsIn(playlistIds);
//        Map<UUID, List<ContentDto>> contentsMap = loadContentPort.getContentsMapByPlaylistIds(playlistIds).map(ContentDto::toDto);
        List<PlaylistDto> data = playlists.stream()
                .map(playlist -> PlaylistDto.toDto(
                        playlist,
                        null, // 목록 조회에선 사용되지 않음
                        false // 목록 조회에선 사용되지 않음
                        // contentsMap.getOrDefault(playlist.getId(), Collections.emptyList())
                )).toList();

        String nextCursor = null;
        String nextIdAfter = null;
        if (!playlists.isEmpty() && playlists.hasNext()) {
            Playlist lastPlaylist = playlists.getContent().get(playlists.getContent().size() - 1);

            nextCursor = switch (sortBy) {
                case "subscribeCount" -> String.valueOf(lastPlaylist.getSubscriberCount());
                default -> lastPlaylist.getUpdatedAt().toString();
            };
            nextIdAfter = lastPlaylist.getId().toString();
        }

        return new CursorResponsePlaylistDto(
                data,
                nextCursor,
                nextIdAfter,
                playlists.hasNext(),
                0, // 사용되지 않음.
                sortBy,
                CursorResponsePlaylistDto.SortOrder.valueOf(sortDirection)
        );
    }

    @Override
    public PlaylistDto get(String playlistId, UUID viewerId) {

        UUID playlistUUID = parseUUID(playlistId);
        Playlist playlist = loadSinglePlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));

        UserSummaryDto owner = getUserSummaryDto(playlist.getOwnerId());

        SubscriptionId id = new SubscriptionId(playlistUUID, viewerId);
        boolean subscribedByMe = loadSubscriptionPort.existsById(id);

//      List<ContentDto> contents = getContents(playlistUUID);

        return PlaylistDto.toDto(playlist, owner, subscribedByMe);
    }

    private UserSummaryDto getUserSummaryDto(UUID ownerId){
        Profile profile = loadProfilePort.load(ownerId)
                .orElseThrow(() -> new ProfileNotFoundException(ownerId));
        String profileImageUrl = (profile.getTimeoutImage() != null) ? profile.getTimeoutImage().getPresignedUrl() : null;

        return new UserSummaryDto(
                ownerId,
                profile.getName(),
                profileImageUrl
        );
    }

//    private List<ContentDto> getContents(UUID playlistId){
//        List<UUID> contentIds = loadCurationPort.findAllByPlaylistId(playlistId);
//        return loadContentsPort.findAllByIdIn(contentIds)
//                .stream().map(ContentDto::toDto).toList();
//    }

    private UUID parseUUID(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }


}

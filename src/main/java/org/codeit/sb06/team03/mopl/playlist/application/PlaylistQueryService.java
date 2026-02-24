package org.codeit.sb06.team03.mopl.playlist.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidIdentifierException;
import org.codeit.sb06.team03.mopl.playlist.PlaylistReadModel;
import org.codeit.sb06.team03.mopl.playlist.application.in.GetSinglePlaylistUseCase;
import org.codeit.sb06.team03.mopl.playlist.application.in.GetPlaylistsUseCase;
import org.codeit.sb06.team03.mopl.playlist.application.in.GetSubscriptionUseCase;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadCurationPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSinglePlaylistPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadPlaylistsPort;
import org.codeit.sb06.team03.mopl.playlist.application.out.LoadSubscriptionPort;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Playlist;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.SubscriptionId;
import org.codeit.sb06.team03.mopl.playlist.domain.exception.PlaylistNotFoundException;
import org.codeit.sb06.team03.mopl.playlist.infra.in.request.CursorRequestPlaylistDto;
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
public class PlaylistQueryService implements GetPlaylistsUseCase, GetSinglePlaylistUseCase, GetSubscriptionUseCase {

    private final LoadPlaylistsPort loadPlaylistsPort;
    private final LoadSinglePlaylistPort loadSinglePlaylistPort;
    private final LoadCurationPort loadCurationPort;
    private final LoadSubscriptionPort loadSubscriptionPort;

    @Override
    public Slice<PlaylistReadModel> get(CursorRequestPlaylistDto request, UUID viewerId) {

        final String keywordLike = request.keywordLike();
        final UUID ownerIdEqual = parseUUID(request.ownerIdEqual());
        final UUID subscriberIdEqual = parseUUID(request.subscriberIdEqual());
        final String cursor = StringUtils.hasText(request.cursor()) ? request.cursor() : null;
        final UUID idAfter = parseUUID(request.idAfter());
        final int limit = request.limit();
        final String sortDirection = request.sortDirection();
        final String sortBy = request.sortBy();

        return loadPlaylistsPort.findAll(
                keywordLike,
                ownerIdEqual,
                subscriberIdEqual,
                cursor,
                idAfter,
                limit,
                sortDirection,
                sortBy
        );
//        List<UUID> playlistIds = slice.getContent().stream().map(Playlist::getId).toList();
//        Map<UUID, List<UUID>> contentIds = loadCurationPort.findAllByPlaylistIdsIn(playlistIds);
////        Map<UUID, List<ContentDto>> contentsMap = loadContentPort.getContentsMapByPlaylistIds(playlistIds).map(ContentDto::toDto);
//        Map<UUID, Boolean> subscriptionMap = (viewerId != null)
//                ? loadSubscriptionPort.existsByIdIn(playlistIds, viewerId)
//                : Collections.emptyMap();
//
//        List<PlaylistDto> data = playlists.stream()
//                .map(playlist -> PlaylistDto.toDto(
//                        playlist,
//                        getUserSummaryDto(playlist.getOwnerId()),
//                        subscriptionMap.getOrDefault(playlist.getId(), false)
//                        // contentsMap.getOrDefault(playlist.getId(), Collections.emptyList())
//                )).toList();
//
//        String nextCursor = null;
//        UUID nextIdAfter = null;
//        if (!playlists.isEmpty() && playlists.hasNext()) {
//            Playlist lastPlaylist = playlists.getContent().get(playlists.getContent().size() - 1);
//
//            nextCursor = switch (sortBy) {
//                case "subscribeCount" -> String.valueOf(lastPlaylist.getSubscriberCount());
//                default -> lastPlaylist.getUpdatedAt().toString();
//            };
//            nextIdAfter = lastPlaylist.getId();
//        }
    }

    @Override
    public PlaylistReadModel get(String playlistId, UUID viewerId) {

        UUID playlistUUID = parseUUID(playlistId);
        Playlist playlist = loadSinglePlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        return PlaylistReadModel.from(playlist);
    }

    @Override
    public boolean isSubscribed(String playlistId, UUID viewerId) {

        UUID playlistUUID = parseUUID(playlistId);
        SubscriptionId id = new SubscriptionId(playlistUUID, viewerId);
        return loadSubscriptionPort.existsById(id);
    };

    @Override
    public Map<UUID, Boolean> isSubscribed(List<UUID> playlistIds, UUID viewerId) {
        return loadSubscriptionPort.existsByIdIn(playlistIds, viewerId);
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

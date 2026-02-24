package org.codeit.sb06.team03.mopl.playlist.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidIdentifierException;
import org.codeit.sb06.team03.mopl.playlist.application.in.*;
import org.codeit.sb06.team03.mopl.playlist.application.out.*;
import org.codeit.sb06.team03.mopl.playlist.domain.CurationService;
import org.codeit.sb06.team03.mopl.playlist.domain.SubscriptionService;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.*;
import org.codeit.sb06.team03.mopl.playlist.domain.PlaylistService;
import org.codeit.sb06.team03.mopl.playlist.domain.event.PlaylistEvent;
import org.codeit.sb06.team03.mopl.playlist.domain.exception.*;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.PlaylistDto;
import org.codeit.sb06.team03.mopl.playlist.infra.in.response.UserSummaryDto;
import org.codeit.sb06.team03.mopl.user.application.out.LoadProfilePort;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.exception.ProfileNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// TODO 사용자 삭제되면 구독 삭제, 컨텐츠 삭제되면 큐레이션 삭제

@RequiredArgsConstructor
@Service
@Transactional
public class PlaylistCommandService implements CreatePlaylistUseCase, UpdatePlaylistUseCase, DeletePlaylistUseCase, AddContentToCurationUseCase, DeleteContentFromCurationUseCase,SubscribePlaylistUseCase, UnsubscribePlaylistUseCase {

    private final SavePlaylistPort savePlaylistPort;
    private final SaveSubscriptionPort saveSubscriptionPort;
    private final SaveCurationPort saveCurationPort;
    private final LoadSinglePlaylistPort loadPlaylistPort;
    private final LoadCurationPort loadCurationPort;
    private final LoadSubscriptionPort loadSubscriptionPort;
    private final LoadProfilePort loadProfilePort;

    private final PlaylistService playlistService;
    private final CurationService curationService;
    private final SubscriptionService subscriptionService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Playlist create(CreatePlaylistCommand command, UUID ownerId) {

        final String title = command.title();
        final String description = command.description();

        Playlist playlist = playlistService.create(title, description, ownerId);
        savePlaylistPort.save(playlist);
        return playlist;
    }

    @Override
    public Playlist update(String playlistId, UpdatePlaylistCommand command, UUID ownerId) {

        UUID playlistUUID = parseUUID(playlistId);
        final String title = command.title();
        final String description = command.description();

        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                        .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        if (!playlist.getOwnerId().equals(ownerId)) {
            throw new PlaylistAccessDeniedException(playlistUUID, ownerId);
        }

        playlist = playlistService.update(playlist, title, description);
        savePlaylistPort.save(playlist);

        return playlist;
    }

    @Override
    public void delete(String playlistId, UUID ownerId) {
        UUID playlistUUID = parseUUID(playlistId);
        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        if (!playlist.getOwnerId().equals(ownerId)) {
            throw new PlaylistAccessDeniedException(playlistUUID, ownerId);
        }
        savePlaylistPort.delete(playlistUUID);
        eventPublisher.publishEvent(new PlaylistEvent.PlaylistDeletedEvent(playlistUUID));
    }

    @Override
    public void addContentToPlaylist(String playlistId, String contentId, UUID ownerId) {

        UUID playlistUUID = parseUUID(playlistId);
        UUID contentUUID = parseUUID(contentId);

        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        if (!playlist.getOwnerId().equals(ownerId)) {
            throw new PlaylistAccessDeniedException(playlistUUID, ownerId);
        }
        // TODO loadContentPort.existsById()

        if (loadCurationPort.existsById(new CurationId(playlistUUID, contentUUID))) {
            throw new ContentAlreadyBeenCuratedException(playlistUUID, contentUUID);
        }

        Curation curation = curationService.create(playlistUUID, contentUUID);
        saveCurationPort.save(curation);

        playlist.increaseContentCount();
        savePlaylistPort.save(playlist);

        eventPublisher.publishEvent(new PlaylistEvent.CurationAddedEvent(playlist.getId(), playlist.getTitle()));
        // TODO 컨텐츠 이름도 전달?
    }

    @Override
    public void deleteContentFromPlaylist(String playlistId, String contentId, UUID ownerId) {

        UUID playlistUUID = parseUUID(playlistId);
        UUID contentUUID = parseUUID(contentId);

        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                        .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        if (!playlist.getOwnerId().equals(ownerId)) {
            throw new PlaylistAccessDeniedException(playlistUUID, ownerId);
        }

        CurationId id = new CurationId(playlistUUID, contentUUID);
        loadCurationPort.findById(id)
                .orElseThrow(() -> new CurationNotFoundException(playlistUUID, contentUUID));

        saveCurationPort.delete(id);

        playlist.decreaseContentCount();
        savePlaylistPort.delete(playlistUUID);
    }

    @Override
    public void subscribe(String playlistId, UUID userId) {

        UUID playlistUUID = parseUUID(playlistId);
        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));
        Profile subscriber = loadProfilePort.load(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));

        SubscriptionId id = new SubscriptionId(playlistUUID, userId);
        if (loadSubscriptionPort.existsById(id)){
            throw new SubscriptionAlreadyExistsException(playlistUUID, userId);
        }
        if (playlist.getOwnerId().equals(userId)){
            throw new SelfSubscriptionNotAllowedException(playlistUUID, userId);
        }

        Subscription subscription = subscriptionService.create(playlistUUID, userId);
        saveSubscriptionPort.save(subscription);

        playlist.increaseSubscriberCount();
        savePlaylistPort.save(playlist);

        eventPublisher.publishEvent(new PlaylistEvent.SubscriptionCreatedEvent(
                playlistUUID,
                playlist.getTitle(),
                userId,
                subscriber.getName(),
                playlist.getOwnerId()
        ));
    }

    @Override
    public void unsubscribe(String playlistId, UUID userId) {

        UUID playlistUUID = parseUUID(playlistId);
        Playlist playlist = loadPlaylistPort.findById(playlistUUID)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistUUID));

        SubscriptionId id = new SubscriptionId(playlistUUID, userId);
        if (!loadSubscriptionPort.existsById(id)){
                throw new SubscriptionNotFoundException(playlistUUID, userId);
        }
        saveSubscriptionPort.delete(id);

        playlist.decreaseSubscriberCount();
        savePlaylistPort.save(playlist);
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

//    private List<ContentDto> getContents(playlistUUID){
//        List<UUID> contentIds = loadCurationPort.findAllByPlaylistId();
//        return loadContentsPort.findAllByIdIn(contentIds)
//                .stream().map(ContentDto::toDto).toList();
//    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }
}

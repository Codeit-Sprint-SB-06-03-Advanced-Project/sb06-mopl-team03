package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.liveChat.application.in.SendPresenceMessageUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendPresenceMessageCommand;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.CreateWatchingSessionCommand;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.CreateWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.DeleteWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.GetWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LiveChatWebEventListener {

    private final CreateWatchingSessionUseCase createWatchingSessionUseCase;
    private final SendPresenceMessageUseCase sendPresenceMessageUseCase;
    private final DeleteWatchingSessionUseCase deleteWatchingSessionUseCase;
    private final GetWatchingSessionUseCase getWatchingSessionUseCase;

    // 같은 채널을 구독하지 못하게 하는 로직 필요
    @EventListener
    void onLiveChatSubscribedEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) {
            return; // null 가능성 없음
        }

        String destination = accessor.getDestination();
        if (destination == null || !DestinationUtils.matchWatchSubDestination(destination)) {
            return; // null 가능성 없음
        }

        if (event.getUser() == null) {
            return; // null 가능성 없음
        }

        UUID contentId = UUID.fromString(DestinationUtils.extractContentId(destination));
        UUID liveChatId = contentId; // LiveChat은 Content와 같은 ID를 쓰고 있음.

        MoplUserDetails userDetails = getUserDetails(event.getUser());
        UserDto userDto = userDetails.getUserDto();

        CreateWatchingSessionCommand createWatchingSessionCommand = new CreateWatchingSessionCommand(liveChatId, userDto.id());

        createWatchingSessionUseCase.create(createWatchingSessionCommand);

        WatchingSession watchingSession = getWatchingSessionUseCase.get(liveChatId, userDto.id());

        SendPresenceMessageCommand sendPresenceMessageCommand =
                new SendPresenceMessageCommand(
                        watchingSession.getId(),
                        watchingSession.getCreatedAt(),
                        userDto.id(),
                        userDto.name(),
                        userDto.profileImageUrl(),
                        WatchType.JOIN.name(),
                        destination
                );
        sendPresenceMessageUseCase.sendPresenceMessage(liveChatId, sendPresenceMessageCommand);
    }

    @EventListener
    void onLiveChatUnSubscribedEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) {
            return; // null 가능성 없음
        }

        String destination = (String) accessor.getSessionAttributes().get(accessor.getSubscriptionId());

        if (destination == null || !DestinationUtils.matchWatchSubDestination(destination)) {
            return;
        }

        accessor.getSessionAttributes().remove(accessor.getSubscriptionId());

        if (event.getUser() == null) {
            return; // null 가능성 없음
        }

        MoplUserDetails userDetails = getUserDetails(event.getUser());
        UserDto userDto = userDetails.getUserDto();

        UUID contentId = UUID.fromString(DestinationUtils.extractContentId(destination));
        UUID liveChatId = contentId; // LiveChat은 Content와 같은 ID를 쓰고 있음.

        WatchingSession watchingSession = getWatchingSessionUseCase.get(liveChatId, userDto.id());

        deleteWatchingSessionUseCase.delete(watchingSession.getId());

        SendPresenceMessageCommand sendPresenceMessageCommand =
                new SendPresenceMessageCommand(
                        watchingSession.getId(),
                        watchingSession.getCreatedAt(),
                        userDto.id(),
                        userDto.name(),
                        userDto.profileImageUrl(),
                        WatchType.LEAVE.name(),
                        destination
                );

        sendPresenceMessageUseCase.sendPresenceMessage(liveChatId, sendPresenceMessageCommand);
    }

    @EventListener
    void onLiveChatDisconnectedEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) {
            return; // null 가능성 없음
        }

        if (event.getUser() == null) {
            return; // user가 null인 경우는 connect에서 setUser를 하기 전에 종료되었을 때 뿐임, 그러므로 데이터베이스 작업은 불필요함
        }

        if (accessor.getSessionAttributes() == null || accessor.getSessionAttributes().isEmpty()) {
            return;
        }

        MoplUserDetails userDetails = getUserDetails(event.getUser());
        UserDto userDto = userDetails.getUserDto();

        List<String> destinations = accessor.getSessionAttributes().values()
                .stream().map(value -> (String) value)
                .filter(DestinationUtils::matchWatchSubDestination)
                .toList();

        if (destinations.isEmpty()) {
            return;
        }

        List<WatchingSession> watchingSessions = getWatchingSessionUseCase.get(userDto.id());
        deleteWatchingSessionUseCase.deleteByWatcherId(userDto.id());

        // FIXME: 보완 필요
        destinations.forEach(destination -> {
            UUID contentId = UUID.fromString(DestinationUtils.extractContentId(destination));
            UUID liveChatId = contentId;

            WatchingSession watchingSession = getWatchingSession(watchingSessions, liveChatId, userDto.id());

            if (watchingSession != null) {
                SendPresenceMessageCommand sendPresenceMessageCommand =
                        new SendPresenceMessageCommand(
                                watchingSession.getId(),
                                watchingSession.getCreatedAt(),
                                userDto.id(),
                                userDto.name(),
                                userDto.profileImageUrl(),
                                WatchType.LEAVE.name(),
                                destination
                        );
                sendPresenceMessageUseCase.sendPresenceMessage(liveChatId, sendPresenceMessageCommand);
            }
        });
    }

    private MoplUserDetails getUserDetails(Principal principal) {
        Authentication authentication = (Authentication) principal;
        return (MoplUserDetails) authentication.getPrincipal();
    }

    private WatchingSession getWatchingSession(List<WatchingSession> watchingSessions, UUID liveChatId, UUID watcherId) {
        return watchingSessions.stream()
                .filter(watchingSession ->
                        watchingSession.getLiveChatId().equals(liveChatId) &&
                                watchingSession.getWatcherId().equals(watcherId))
                .findFirst()
                .orElse(null);
    }
}

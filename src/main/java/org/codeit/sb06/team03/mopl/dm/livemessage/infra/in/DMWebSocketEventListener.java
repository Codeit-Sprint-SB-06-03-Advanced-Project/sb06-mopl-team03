package org.codeit.sb06.team03.mopl.dm.livemessage.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.LiveMessageJoinCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.LiveMessageJoinUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.LiveMessageLeaveCommand;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.LiveMessageLeaveUseCase;
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
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DMWebSocketEventListener {

    private static final Pattern DM_SUB_PATTERN = Pattern.compile("^/sub/conversations/[0-9a-fA-F-]+/direct-messages$");

    private final LiveMessageJoinUseCase liveMessageJoinUseCase;
    private final LiveMessageLeaveUseCase liveMessageLeaveUseCase;

    @EventListener
    void onSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) return;

        String destination = accessor.getDestination();
        if (destination == null || !DM_SUB_PATTERN.matcher(destination).matches()) return;
        if (event.getUser() == null) return;

        UUID conversationId = extractConversationId(destination);
        UUID userId = getUserId(event.getUser());

        liveMessageJoinUseCase.join(new LiveMessageJoinCommand(conversationId, userId));
    }

    @EventListener
    void onUnsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) return;

        String destination = (String) accessor.getSessionAttributes().get(accessor.getSubscriptionId());
        if (destination == null || !DM_SUB_PATTERN.matcher(destination).matches()) return;

        accessor.getSessionAttributes().remove(accessor.getSubscriptionId());

        if (event.getUser() == null) return;

        UUID conversationId = extractConversationId(destination);
        UUID userId = getUserId(event.getUser());

        liveMessageLeaveUseCase.leave(new LiveMessageLeaveCommand(conversationId, userId));
    }

    @EventListener
    void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor == null) return;
        if (event.getUser() == null) return;
        if (accessor.getSessionAttributes() == null || accessor.getSessionAttributes().isEmpty()) return;

        UUID userId = getUserId(event.getUser());

        List<String> dmDestinations = accessor.getSessionAttributes().values().stream()
                .filter(v -> v instanceof String)
                .map(v -> (String) v)
                .filter(d -> DM_SUB_PATTERN.matcher(d).matches())
                .toList();

        dmDestinations.forEach(destination -> {
            UUID conversationId = extractConversationId(destination);
            liveMessageLeaveUseCase.leave(new LiveMessageLeaveCommand(conversationId, userId));
        });
    }

    private UUID extractConversationId(String destination) {
        String[] parts = destination.split("/");
        return UUID.fromString(parts[3]);
    }

    private UUID getUserId(Principal principal) {
        Authentication authentication = (Authentication) principal;
        MoplUserDetails userDetails = (MoplUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompContentInboundInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();

        if (command == null) {
            return message;
        }

        return switch (command) {
            case SEND -> handleSend(accessor, message);
            case SUBSCRIBE -> handleSubscribe(accessor, message);
            default -> message;
        };
    }

    private Message<?> handleSend(StompHeaderAccessor accessor, Message<?> message) {
        String destination = accessor.getDestination();

        if (destination == null) {
            throw new SecurityException("잘못된 전송 경로 입니다.");

        }
        if (DestinationUtils.isContentDestination(destination)) {
            if (!DestinationUtils.matchSendRequestDestination(destination)) {
                throw new SecurityException("잘못된 전송 경로 입니다.");
            }
        }

        return message;
    }

    private Message<?> handleSubscribe(StompHeaderAccessor accessor, Message<?> message) {
        String destination = accessor.getDestination();

        if (destination == null) {
            throw new SecurityException("잘못된 구독 경로 입니다.");

        }

        if (DestinationUtils.isContentDestination(destination)) {
            if (!DestinationUtils.matchSubDestination(destination)) {
                throw new SecurityException("잘못된 구독 경로 입니다.");
            }
        }

        accessor.getSessionAttributes().put(accessor.getSubscriptionId(), destination);

        return message;
    }
}

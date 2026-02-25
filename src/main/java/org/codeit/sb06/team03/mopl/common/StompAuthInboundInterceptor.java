package org.codeit.sb06.team03.mopl.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtClaims;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtTokenProvider;
import org.codeit.sb06.team03.mopl.common.security.jwt.exception.InvalidTokenException;
import org.codeit.sb06.team03.mopl.common.security.jwt.registry.JwtRegistry;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class StompAuthInboundInterceptor implements ChannelInterceptor {

    private final JwtRegistry jwtRegistry;
    private final JwtTokenProvider jwtTokenProvider;

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
            case CONNECT -> handleConnect(accessor, message);
            case SEND -> handleSend(accessor, message);
            case SUBSCRIBE -> handleSubscribe(accessor, message);
            default -> message;
        };
    }

    private Message<?> handleConnect(StompHeaderAccessor accessor, Message<?> message) {
        String accessToken = resolveToken(accessor);

        if (accessToken == null || !jwtRegistry.hasActiveAccessToken(accessToken)) {
            throw new InvalidTokenException();
        }

        accessor.getSessionAttributes().put("accessToken", accessToken);

        JwtClaims jwtClaims = jwtTokenProvider.getClaims(accessToken);
        UserDetails userDetails = createUserDetails(jwtClaims);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        accessor.setUser(authentication);

        return message;
    }

    private Message<?> handleSend(StompHeaderAccessor accessor, Message<?> message) {
        String accessToken = accessor.getSessionAttributes() != null ?
                accessor.getSessionAttributes().get("accessToken").toString() : null;

        if (accessToken == null || !jwtRegistry.hasActiveAccessToken(accessToken)) {
            throw new InvalidTokenException();
        }

        return message;
    }

    private Message<?> handleSubscribe(StompHeaderAccessor accessor, Message<?> message) {
        String accessToken = accessor.getSessionAttributes().get("accessToken").toString(); //null 일 수 없음

        if (!jwtRegistry.hasActiveAccessToken(accessToken)) {
            throw new InvalidTokenException();
        }

        return message;
    }

    private UserDetails createUserDetails(JwtClaims jwtClaims) {
        UserDto userDto = new UserDto(
                jwtClaims.id(),
                null,
                jwtClaims.email(),
                jwtClaims.name(),
                jwtClaims.profileImageUrl(),
                jwtClaims.role(),
                null
        );

        return new MoplUserDetails(userDto, null);
    }

    private String resolveToken(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

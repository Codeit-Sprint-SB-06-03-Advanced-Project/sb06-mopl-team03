package org.codeit.sb06.team03.mopl.dm.livemessage.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffDMService;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.dm.livemessage.infra.in.request.MessageSendRequest;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class DMWebSocketController {

    private final BffDMService bffDMService;

    @MessageMapping("/conversations/{conversationId}/direct-messages")
    public void sendMessage(
            @DestinationVariable String conversationId,
            @Payload MessageSendRequest request,
            Principal principal
    ) {
        Authentication authentication = (Authentication) principal;
        MoplUserDetails userDetails = (MoplUserDetails) authentication.getPrincipal();
        UserDto userDto = userDetails.getUserDto();

        bffDMService.sendMessage(conversationId, userDto.id().toString(), request);
    }
}
package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.liveChat.application.in.SendLiveChatMessageUseCase;
import org.codeit.sb06.team03.mopl.liveChat.application.in.command.SendLiveChatMessageCommand;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LiveChatWebController implements LiveChatApi {

    private final SendLiveChatMessageUseCase sendLiveChatMessageUseCase;

    @MessageMapping("/contents/{contentId}/chat")
    public void pubMessage(
            @DestinationVariable String contentId,
            @Payload LiveChatSendRequest request,
            Principal principal
    ) {
        Authentication authentication = (Authentication) principal;
        MoplUserDetails userDetails = (MoplUserDetails) authentication.getPrincipal();
        UserDto userDto = userDetails.getUserDto();

        String destination = DestinationUtils.liveChatSendResponseDestinationFormat.formatted(contentId);

        SendLiveChatMessageCommand command = new SendLiveChatMessageCommand(
                userDto.id(),
                userDto.name(),
                userDto.profileImageUrl(),
                request.text(),
                destination
        );
        sendLiveChatMessageUseCase.sendLiveChatMessage(command);
    }
}

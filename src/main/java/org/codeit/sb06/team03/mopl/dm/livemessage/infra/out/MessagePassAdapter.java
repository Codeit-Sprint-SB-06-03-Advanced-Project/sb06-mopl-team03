package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DMUserDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DirectMessageDto;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MessagePassPort;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MessagePassAdapter implements MessagePassPort{

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void pass(UUID conversationId, UUID messageId, String content, Instant createdAt, DMUser sender, DMUser receiver) {
        DirectMessageDto dto = new DirectMessageDto(
                messageId.toString(),
                conversationId.toString(),
                createdAt.toString(),
                DMUserDto.from(sender),
                DMUserDto.from(receiver),
                content
        );
        String destination = "/sub/conversations/" + conversationId + "/direct-messages";
        messagingTemplate.convertAndSend(destination, dto);
    }
}
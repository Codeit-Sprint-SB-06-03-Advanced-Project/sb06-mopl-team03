package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.out.LoadDMUserPort;
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
    private final LoadDMUserPort loadDMUserPort;

    @Override
    public void pass(UUID conversationId, UUID receiverId, UUID messageId, UUID senderId, String content, Instant createdAt) {
        DMUser sender = loadDMUserPort.findByUserId(senderId);
        DMUser receiver = loadDMUserPort.findByUserId(receiverId);

        DirectMessageDto dto = new DirectMessageDto(
                messageId.toString(),
                conversationId.toString(),
                createdAt.toString(),
                DMUserDto.from(sender),
                DMUserDto.from(receiver),
                content
        );

        String destination = "/sub/conversations/" + conversationId;
        messagingTemplate.convertAndSend(destination, dto);
    }
}
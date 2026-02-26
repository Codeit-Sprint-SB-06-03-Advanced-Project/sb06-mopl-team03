package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveDMUserPort;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendCommand;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MarkUnreadPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.SaveLiveMessagePort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessage;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LiveMessageCommandService implements MessageSendUseCase {

    private final LiveMessageService liveMessageService;
    private final SaveLiveMessagePort saveLiveMessagePort;
    private final LoadLiveDMUserPort loadDMUserPort;
    private final MarkUnreadPort markUnreadPort;

    @Override
    public LiveMessage send(MessageSendCommand command) {
        DMUser sender = loadDMUserPort.findByUserId(command.senderId());
        DMUser receiver = loadDMUserPort.findByUserId(command.receiverId());

        LiveMessage message = liveMessageService.create(
                command.conversationId(), command.senderId(),
                command.receiverId(), command.content(),
                sender, receiver
        );
        LiveMessage savedMessage = saveLiveMessagePort.save(message);

        markUnreadPort.markAsUnread(command.conversationId(), command.receiverId());

        return savedMessage;
    }
}
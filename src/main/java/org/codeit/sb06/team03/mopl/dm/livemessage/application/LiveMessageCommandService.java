package org.codeit.sb06.team03.mopl.dm.livemessage.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageReceiveUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.in.MessageSendUseCase;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.MessagePassPort;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.LiveMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LiveMessageCommandService implements MessageReceiveUseCase, MessageSendUseCase {

    private final LiveMessageService liveMessageService;
    private final MessagePassPort messagePassPort;
}

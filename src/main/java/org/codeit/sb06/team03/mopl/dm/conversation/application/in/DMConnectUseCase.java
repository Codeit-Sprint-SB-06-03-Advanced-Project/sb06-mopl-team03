package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;

public interface DMConnectUseCase {

    ConversationDto connect(DMConnectCommand command);
}

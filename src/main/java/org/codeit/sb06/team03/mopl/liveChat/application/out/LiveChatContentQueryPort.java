package org.codeit.sb06.team03.mopl.liveChat.application.out;

import org.codeit.sb06.team03.mopl.common.ContentResult;

import java.util.UUID;

public interface LiveChatContentQueryPort {

    ContentResult findById(UUID contentId);
}

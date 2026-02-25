package org.codeit.sb06.team03.mopl.content.application.in;

import org.codeit.sb06.team03.mopl.content.Content;

import java.util.UUID;

public interface GetContentUseCase {

    Content get(UUID contentId);

    CursorResponseWatchingSessionDto get(WatchingSessionCursorCommand command);
}
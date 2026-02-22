package org.codeit.sb06.team03.mopl.playlist.domain.exception;

import java.util.UUID;

public class PlaylistAccessDeniedException extends PlaylistException {
    public PlaylistAccessDeniedException(UUID userId) {
        super("플레이리스트는 소유자만 수정/삭제할 수 있습니다.");
    }
}

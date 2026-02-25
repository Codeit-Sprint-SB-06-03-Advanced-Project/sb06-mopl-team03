package org.codeit.sb06.team03.mopl.liveChat.application.in;

import java.util.UUID;

// TODO: 컨텐츠 삭제 될 때 같이 삭제 필요
public interface DeleteLiveChatUseCase {

    void delete(UUID contentId);
}

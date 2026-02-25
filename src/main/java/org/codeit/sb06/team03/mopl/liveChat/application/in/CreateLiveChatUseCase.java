package org.codeit.sb06.team03.mopl.liveChat.application.in;

import java.util.UUID;

// TODO: 컨텐츠 생성 될 때 같이 create 필요
public interface CreateLiveChatUseCase {

    void create(UUID contentId);
}

package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import org.hibernate.validator.constraints.UUID;

import java.security.Principal;

public interface LiveChatApi {

    void pubMessage(
            @UUID(message = "잘못된 UUID 형식입니다.") String contentId,
            LiveChatSendRequest request,
            Principal principal
    );
}
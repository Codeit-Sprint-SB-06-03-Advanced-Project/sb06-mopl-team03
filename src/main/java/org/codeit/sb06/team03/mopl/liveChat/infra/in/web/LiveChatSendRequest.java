package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LiveChatSendRequest(
        @JsonProperty("content")
        String text
) {
}

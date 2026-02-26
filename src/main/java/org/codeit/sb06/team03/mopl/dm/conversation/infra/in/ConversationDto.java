package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

public record ConversationDto(
        @Schema(description = "대화 ID", format = "uuid")
        String id,

        @Schema(description = "대화 상대 정보")
        DMUserDto with,

        @Nullable
        @Schema(description = "마지막 메시지 내용")
        DirectMessageDto lastestMessage,

        @Schema(description = "읽지 않은 메시지 존재 여부")
        Boolean hasUnread
) {
}
package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;

public record ConversationCreateRequest(
        @Schema(description = "대화 상대 정보 ID")
        String withUserId
) {
}
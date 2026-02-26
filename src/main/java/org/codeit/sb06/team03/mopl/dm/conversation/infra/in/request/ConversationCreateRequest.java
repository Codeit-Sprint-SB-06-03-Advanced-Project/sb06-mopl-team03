package org.codeit.sb06.team03.mopl.dm.conversation.infra.in.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ConversationCreateRequest(
        @Schema(description = "대화 상대 정보 ID")
        @NotBlank
        String withUserId
) {
}
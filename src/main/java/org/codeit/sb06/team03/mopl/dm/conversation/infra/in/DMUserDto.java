package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;

public record DMUserDto(
        @Schema(description = "사용자 ID")
        String userId,

        @Schema(description = "사용자 이름")
        String name,

        @Schema(description = "사용자 프로필 이미지 URL")
        String profileImageUrl
) {
}

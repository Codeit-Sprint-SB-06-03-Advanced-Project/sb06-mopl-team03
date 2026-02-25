package org.codeit.sb06.team03.mopl.dm.conversation.domain.vo;

import java.util.UUID;

public record DMUser(
        UUID userId,
        String name,
        String profileImageUrl
) {
}

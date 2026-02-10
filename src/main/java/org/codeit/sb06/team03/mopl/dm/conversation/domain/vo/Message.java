package org.codeit.sb06.team03.mopl.dm.conversation.domain.vo;

import java.time.Instant;

public record Message(
        String content,
        DMUser sender,
        Instant createdAt
) {
}

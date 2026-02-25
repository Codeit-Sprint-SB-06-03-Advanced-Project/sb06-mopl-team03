package org.codeit.sb06.team03.mopl.common;

import java.time.Instant;
import java.util.UUID;

public record SessionDetails(
        UUID id,
        Instant createdAt,
        UserSummary watcher,
        ContentResult content
) {

}
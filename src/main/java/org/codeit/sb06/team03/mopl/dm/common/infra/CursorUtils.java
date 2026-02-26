package org.codeit.sb06.team03.mopl.dm.common.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.DateTimePath;

import java.time.Instant;
import java.util.UUID;

public class CursorUtils {
    public static BooleanExpression buildCursorCondition(
            DateTimePath<Instant> createdAt,
            ComparablePath<UUID> id,
            String cursor, String idAfter, boolean isAsc) {
        if (cursor == null) return null;
        Instant cursorTime = Instant.parse(cursor);
        UUID idAfterUuid = idAfter == null ? null : UUID.fromString(idAfter);

        if (isAsc) {
            BooleanExpression cond = createdAt.gt(cursorTime);
            if (idAfterUuid != null)
                cond = cond.or(createdAt.eq(cursorTime).and(id.gt(idAfterUuid)));
            return cond;
        } else {
            BooleanExpression cond = createdAt.lt(cursorTime);
            if (idAfterUuid != null)
                cond = cond.or(createdAt.eq(cursorTime).and(id.lt(idAfterUuid)));
            return cond;
        }
    }
}

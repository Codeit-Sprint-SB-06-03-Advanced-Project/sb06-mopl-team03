package org.codeit.sb06.team03.mopl.common;

import java.util.List;
import java.util.UUID;

public record ContentResult(
        UUID id,
        String type,
        String title,
        String description,
        String thumbnailUrl,
        List<String> tags,
        double averageRating,
        int reviewCount
) {
}
package org.codeit.sb06.team03.mopl.common;

import org.codeit.sb06.team03.mopl.content.Content;
import org.codeit.sb06.team03.mopl.content.domain.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContentMapper {

    public ContentResult toContentResult(Content content) {
        List<String> tags = content.getTags().stream().map(Tag::getName).toList();
        int reviewCount = content.getReviewStats().getReviewCount();
        long ratingSum = content.getReviewStats().getRatingSum();


        return new ContentResult(
                content.getId(),
                content.getType().name(),
                content.getTitle(),
                content.getDescription(),
                content.getThumbnailImage(),
                tags,
                calculateReviewAverage(ratingSum, reviewCount),
                reviewCount
        );
    }

    private double calculateReviewAverage(long ratingSum, int reviewCount) {
        if (reviewCount <= 0 || ratingSum <= 0) {
            return 0;
        }
        return (double) ratingSum / reviewCount;
    }
}

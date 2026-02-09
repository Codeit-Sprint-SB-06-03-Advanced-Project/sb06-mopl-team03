package org.codeit.sb06.team03.mopl.content.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.content.Content;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "review_stats")
public class ReviewStats {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Content content;

    @Column(name = "rating_sum", nullable = false)
    private long ratingSum;

    @Column(name = "review_count", nullable = false)
    private int reviewCount;

    @Column(name = "version", nullable = false)
    private short version;
}

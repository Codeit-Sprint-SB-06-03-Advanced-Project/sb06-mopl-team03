package org.codeit.sb06.team03.mopl.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.content.domain.entity.Review;
import org.codeit.sb06.team03.mopl.content.domain.entity.ReviewStats;
import org.codeit.sb06.team03.mopl.content.domain.entity.Tag;
import org.codeit.sb06.team03.mopl.content.domain.vo.Type;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "contents")
public class Content {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "version", nullable = false)
    private short version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false, length = 10_000)
    private String description;

    @NotNull
    @Column(name = "thumbnail_image", nullable = false)
    private String thumbnailImage;

    @ManyToMany
    @JoinTable(
            name = "contents_tags",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;

    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private ReviewStats reviewStats;
}

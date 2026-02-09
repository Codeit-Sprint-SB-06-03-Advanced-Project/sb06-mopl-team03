package org.codeit.sb06.team03.mopl.content.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.content.Content;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @NotNull
    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "version", nullable = false)
    private short version;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "rating", nullable = false)
    private int rating;
}

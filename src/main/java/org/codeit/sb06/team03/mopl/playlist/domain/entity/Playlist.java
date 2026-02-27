package org.codeit.sb06.team03.mopl.playlist.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        name = "playlists",
        indexes = { @Index(name = "idx_updatedAt_cursor", columnList = "updatedAt, id") }
)
public class Playlist {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    @Column(name = "subscriber_count", nullable = false)
    private long subscriberCount;

    @Column(name = "content_count", nullable = false)
    private long contentCount;

    private Playlist(String title, String description, UUID ownerId) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.subscriberCount = 0;
        this.contentCount = 0;
    }

    public static Playlist create(String title, String description, UUID ownerId) {
        return new Playlist(title, description, ownerId);
    }

    public void update(String title, String description) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
        this.updatedAt = Instant.now();
    }

    public void increaseContentCount() {
        this.contentCount++;
        this.updatedAt = Instant.now();
    }

    public void decreaseContentCount() {
        if (contentCount > 0) {
            this.contentCount--;
        }
        this.updatedAt = Instant.now();
    }

    public void increaseSubscriberCount() {
        this.subscriberCount++;
    }

    public void decreaseSubscriberCount() {
        if (subscriberCount > 0) {
            this.subscriberCount--;
        }
    }
}

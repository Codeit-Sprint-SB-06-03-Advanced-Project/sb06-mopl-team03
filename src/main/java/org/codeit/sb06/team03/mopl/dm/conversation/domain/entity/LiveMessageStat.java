package org.codeit.sb06.team03.mopl.dm.conversation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "live_message_stats", uniqueConstraints = @UniqueConstraint(columnNames = {"conversation_id", "account_id"}))
public class LiveMessageStat {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @NotNull
    @Column(name = "activity", nullable = false)
    private boolean activity;

    @NotNull
    @Column(name = "has_unread", nullable = false)
    private boolean hasUnread;

    public static LiveMessageStat create(Conversation conversation, UUID accountId) {
        var liveMessageStat = new LiveMessageStat();
        liveMessageStat.id = UUID.randomUUID();
        liveMessageStat.conversation = conversation;
        liveMessageStat.updatedAt = Instant.now();
        liveMessageStat.accountId = accountId;
        liveMessageStat.activity = false;
        liveMessageStat.hasUnread = false;
        return liveMessageStat;
    }

    public void updateActivity(boolean activity) {
        this.activity = activity;
        this.updatedAt = Instant.now();
    }

    public void markAsRead() {
        this.hasUnread = false;
        this.updatedAt = Instant.now();
    }

    public void markAsUnread() {
        this.hasUnread = true;
        this.updatedAt = Instant.now();
    }
}

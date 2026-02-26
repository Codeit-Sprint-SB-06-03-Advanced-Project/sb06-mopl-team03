package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.event.ConversationEvent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Conversation extends AbstractAggregateRoot<Conversation> {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "accountId")
    private Map<UUID, LiveMessageStat> liveMessageStats = new HashMap<>();

    public static Conversation create(UUID userId, UUID withUserId) {
        var conversation = new Conversation();
        conversation.id = UUID.randomUUID();
        conversation.addStat(withUserId);
        conversation.addStat(userId);
        conversation.registerEvent(new ConversationEvent.ConversationCreatedEvent(conversation.id, userId, withUserId));
        return conversation;
    }

    public void joinLiveMessage(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        if (stat != null) {
            stat.updateActivity(true);
            registerEvent(new ConversationEvent.LiveMessageJoinedEvent(this.id, userId));
        }
    }

    public void leaveLiveMessage(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        if (stat != null) {
            stat.updateActivity(false);
            registerEvent(new ConversationEvent.LiveMessageLeavedEvent(this.id, userId));
        }
    }

    public void markAsRead(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        if (stat != null) {
            stat.markAsRead();
            registerEvent(new ConversationEvent.MessageReadedEvent(this.id, userId));
        }
    }

    public void markAsUnread(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        if (stat != null) {
            stat.markAsUnread();
        }
    }

    public boolean isActive(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        return stat != null && stat.isActivity();
    }

    public UUID getOtherParticipant(UUID requesterId) {
        return this.liveMessageStats.keySet().stream()
                .filter(id -> !id.equals(requesterId))
                .findFirst()
                .orElseThrow();
    }

    private void addStat(UUID accountId) {
        this.liveMessageStats.put(accountId, LiveMessageStat.create(this, accountId));
    }
}

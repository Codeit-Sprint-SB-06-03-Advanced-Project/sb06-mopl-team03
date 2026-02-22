package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.entity.LiveMessageStat;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.event.ConversationEvent;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.Message;
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

    @Embedded
    private DMUser with;

    @Column(name = "has_unread", nullable = false)
    private boolean hasUnread;

    @Embedded
    private Message lastestMessage;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "accountId")
    private Map<UUID, LiveMessageStat> liveMessageStats = new HashMap<>();

    public static Conversation create(DMUser with, UUID userId) {
        var conversation = new Conversation();
        conversation.id = UUID.randomUUID();
        conversation.with = with;
        conversation.hasUnread = false;
        conversation.addStat(with.userId());
        conversation.addStat(userId);
        conversation.registerEvent(new ConversationEvent.ConversationConnectedEvent(conversation.id.toString()));
        return conversation;
    }

    public void updateLastestMessage(Message message) {
        this.lastestMessage = message;
    }

    public void markAsRead() {
        this.hasUnread = false;
        registerEvent(new ConversationEvent.MessageReadEvent());
    }

    public void markAsUnread() {
        this.hasUnread = true;
    }

    public void addStat(UUID accountId) {
        this.liveMessageStats.put(accountId, LiveMessageStat.create(this, accountId));
    }
}

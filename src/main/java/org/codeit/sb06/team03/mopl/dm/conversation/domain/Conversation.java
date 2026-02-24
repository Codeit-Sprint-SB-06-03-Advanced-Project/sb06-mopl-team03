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
    private Message lastestMessage;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "accountId")
    private Map<UUID, LiveMessageStat> liveMessageStats = new HashMap<>();

    public static Conversation create(UUID userId, UUID withUserId) {
        var conversation = new Conversation();
        conversation.id = UUID.randomUUID();
        conversation.addStat(withUserId);
        conversation.addStat(userId);
        conversation.registerEvent(new ConversationEvent.ConversationConnectedEvent(conversation.id.toString()));
        return conversation;
    }

    public void updateLastestMessage(Message message) {
        this.lastestMessage = message;
    }

    public void markAsRead(UUID userId) {
        LiveMessageStat stat = this.liveMessageStats.get(userId);
        if (stat != null) stat.markAsRead();
        registerEvent(new ConversationEvent.MessageReadEvent());
    }

    public void markAsUnread(UUID receiverId) {
        LiveMessageStat stat = this.liveMessageStats.get(receiverId);
        if (stat != null) stat.markAsUnread();
    }

    public void addStat(UUID accountId) {
        this.liveMessageStats.put(accountId, LiveMessageStat.create(this, accountId));
    }
}

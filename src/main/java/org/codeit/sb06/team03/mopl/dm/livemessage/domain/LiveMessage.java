package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.event.LiveMessageEvent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LiveMessage extends AbstractAggregateRoot<LiveMessage> {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;

    @NotNull
    @Column(name = "sender_id", nullable = false)
    private UUID senderId;

    @NotNull
    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    @NotNull
    @Column(name = "content", length = 1_000, nullable = false)
    private String content;

    @NotNull
    @Column(name = "has_unread", nullable = false)
    private boolean hasUnread;

    public static LiveMessage create(UUID conversationId, UUID senderId, UUID receiverId, String content, DMUser sender, DMUser receiver) {
        var liveMessage = new LiveMessage();
        liveMessage.id = UUID.randomUUID();
        liveMessage.createdAt = Instant.now();
        liveMessage.conversationId = conversationId;
        liveMessage.senderId = senderId;
        liveMessage.receiverId = receiverId;
        liveMessage.content = content;
        liveMessage.hasUnread = true;
        liveMessage.registerEvent(new LiveMessageEvent.MessageSentEvent(liveMessage.id, conversationId, senderId, receiverId, content, liveMessage.createdAt, sender, receiver));
        return liveMessage;
    }

    public void markAsRead() {
        this.hasUnread = false;
    }

    public void receive() {
        this.registerEvent(new LiveMessageEvent.MessageReceivedEvent(this.id, this.conversationId, this.senderId, this.receiverId));
    }

    public void pass() {
        this.registerEvent(new LiveMessageEvent.MessagePassedEvent(this.id, this.conversationId, this.receiverId, this.content));
    }
}

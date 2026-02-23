package org.codeit.sb06.team03.mopl.liveChat.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "live_chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiveChat extends AbstractAggregateRoot<LiveChat> {

    @Id
    @Column(name = "content_id")
    private UUID contentId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    public static LiveChat create(UUID contentId) {
        LiveChat liveChat = new LiveChat();
        liveChat.contentId = contentId;
        liveChat.createdAt = Instant.now();
        return liveChat;
    }
}

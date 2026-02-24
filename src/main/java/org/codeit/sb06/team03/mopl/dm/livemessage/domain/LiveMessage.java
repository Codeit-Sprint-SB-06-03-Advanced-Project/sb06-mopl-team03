package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.livemessage.domain.vo.DMUser;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="userId", column=@Column(name="sender_user_id")),
            @AttributeOverride(name="name", column=@Column(name="sender_name")),
            @AttributeOverride(name="profileImageUrl", column=@Column(name="sender_profile_image_url"))
    })
    private DMUser sender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="userId", column=@Column(name="receiver_user_id")),
            @AttributeOverride(name="name", column=@Column(name="receiver_name")),
            @AttributeOverride(name="profileImageUrl", column=@Column(name="receiver_profile_image_url"))
    })
    private DMUser receiver;

    @NotNull
    @Column(name = "content", length = 1_000, nullable = false)
    private String content;

    public static LiveMessage create(DMUser sender, DMUser receiver, String content) {
        var liveMessage = new LiveMessage();
        liveMessage.id = UUID.randomUUID();
        liveMessage.sender = sender;
        liveMessage.receiver = receiver;
        liveMessage.content = content;
        return liveMessage;
    }
}

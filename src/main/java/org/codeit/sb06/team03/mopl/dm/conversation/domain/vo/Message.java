package org.codeit.sb06.team03.mopl.dm.conversation.domain.vo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Embeddable
public record Message(

        @Column(name = "last_message_id")
        UUID messageId,

        @NotNull
        @Column(name = "message_created_at")
        Instant createdAt,

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "userId", column = @Column(name = "sender_user_id")),
                @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
                @AttributeOverride(name = "profileImageUrl", column = @Column(name = "sender_profile_image_url"))
        })
        DMUser sender,

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "userId", column = @Column(name = "receiver_user_id")),
                @AttributeOverride(name = "name", column = @Column(name = "receiver_name")),
                @AttributeOverride(name = "profileImageUrl", column = @Column(name = "receiver_profile_image_url"))
        })
        DMUser receiver,

        @NotNull
        @Column(name = "content", length = 1_000)
        String content
) {
}

package org.codeit.sb06.team03.mopl.dm.conversation.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Embeddable
public record DMUser(

        @NotNull
        @Column(name = "user_id", nullable = false)
        UUID userId,

        @NotNull
        @Column(name = "name", nullable = false)
        String name,

        @NotNull
        @Column(name = "profile_image_url", nullable = false)
        String profileImageUrl
) {
}

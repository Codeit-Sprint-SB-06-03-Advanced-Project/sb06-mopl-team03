package org.codeit.sb06.team03.mopl.dm.livemessage.domain.vo;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record DMUser(
        UUID userId,
        String name,
        String profileImageUrl
) {
}

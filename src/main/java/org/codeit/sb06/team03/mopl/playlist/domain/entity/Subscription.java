package org.codeit.sb06.team03.mopl.playlist.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private SubscriptionId id;

    private Subscription(SubscriptionId id) {
        this.id = id;
    }

    public static Subscription create(UUID playlistId, UUID userId) {
        return new Subscription(new SubscriptionId(playlistId, userId));
    }
}

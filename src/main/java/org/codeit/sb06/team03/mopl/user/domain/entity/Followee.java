package org.codeit.sb06.team03.mopl.user.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.user.domain.User;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "followees")
public class Followee {

    @EmbeddedId
    private FolloweeId id;

    @MapsId("followeeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    @MapsId("followerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;
}

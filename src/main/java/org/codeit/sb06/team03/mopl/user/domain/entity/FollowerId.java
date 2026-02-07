package org.codeit.sb06.team03.mopl.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class FollowerId implements Serializable {

    private static final long serialVersionUID = -2194366406706509219L;

    @NotNull
    @Column(name = "followee_id", nullable = false)
    private UUID followeeId;

    @NotNull
    @Column(name = "follower_id", nullable = false)
    private UUID followerId;
}

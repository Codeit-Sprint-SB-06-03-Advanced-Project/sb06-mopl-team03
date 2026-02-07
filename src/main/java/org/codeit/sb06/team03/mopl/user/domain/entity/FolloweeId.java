package org.codeit.sb06.team03.mopl.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class FolloweeId implements Serializable {

    private static final long serialVersionUID = -6865163440983782076L;

    @NotNull
    @Column(name = "followee_id", nullable = false)
    private UUID followeeId;

    @NotNull
    @Column(name = "follower_id", nullable = false)
    private UUID followerId;


}

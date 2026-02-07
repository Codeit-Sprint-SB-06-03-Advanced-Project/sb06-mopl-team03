package org.codeit.sb06.team03.mopl.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codeit.sb06.team03.mopl.user.domain.Followee;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "followers")
public class Follower {

    @EmbeddedId
    private FollowerId id;

    public Follower(FollowerId id) {
        this.id = id;
    }

    @MapsId("followeeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private Followee followee;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return Objects.equals(id, follower.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

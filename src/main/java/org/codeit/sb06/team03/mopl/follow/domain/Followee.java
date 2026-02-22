package org.codeit.sb06.team03.mopl.follow.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.codeit.sb06.team03.mopl.follow.domain.entity.Follower;
import org.codeit.sb06.team03.mopl.follow.domain.entity.FollowerId;
import org.codeit.sb06.team03.mopl.follow.domain.event.FollowEvent.FollowedEvent;
import org.codeit.sb06.team03.mopl.follow.domain.event.FollowEvent.FolloweeCreatedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.codeit.sb06.team03.mopl.follow.domain.event.FollowEvent.UnfollowedEvent;

@Getter
@Setter
@Entity
@Table(name = "followees")
public class Followee extends AbstractAggregateRoot<Followee> {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "followee_count", nullable = false)
    private long followeeCount;

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follower> followers = new HashSet<>();

    public static Followee create(UUID id) {
        var followee = new Followee();
        followee.id = id;
        followee.followeeCount = 0;
        followee.registerEvent(new FolloweeCreatedEvent());
        return followee;
    }

    public void addFollower(UUID followerId) {
        if (id.equals(followerId)) {
            return;
        }
        if (isFollowedBy(followerId)) {
            return;
        }
        var follower = new Follower();
        follower.setId(new FollowerId(this.id, followerId));
        follower.setFollowee(this);
        followers.add(follower);
        followeeCount++;
        super.registerEvent(new FollowedEvent());
    }

    public void removeFollower(UUID followerId) {
        boolean removed = followers.removeIf(follower -> follower.getId().getFollowerId().equals(followerId));
        if (removed) {
            followeeCount--;
            super.registerEvent(new UnfollowedEvent());
        }
    }

    public boolean isFollowedBy(UUID followerId) {
        return followers.stream().anyMatch(follower -> follower.getId().getFollowerId().equals(followerId));
    }
}

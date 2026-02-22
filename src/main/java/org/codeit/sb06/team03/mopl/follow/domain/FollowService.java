package org.codeit.sb06.team03.mopl.follow.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FollowService {

    public Followee create(UUID id) {
        return Followee.create(id);
    }

    public Followee follow(Followee followee, UUID followerId) {
        followee.addFollower(followerId);
        return followee;
    }

    public Followee unfollow(Followee followee, UUID unfollowId) {
        followee.removeFollower(unfollowId);
        return followee;
    }
}

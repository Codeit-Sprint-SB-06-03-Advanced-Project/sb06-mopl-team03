package org.codeit.sb06.team03.mopl.follow.application.in;

public interface GetFollowUseCase {

    boolean followedByMe(FollowQuery query);

    long count(String followeeId);
}

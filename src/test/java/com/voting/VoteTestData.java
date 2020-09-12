package com.voting;

import com.voting.model.Vote;
import com.voting.to.VoteTo;

import java.time.LocalDate;

import static com.voting.RestaurantTestData.FIRST_RESTAURANT_ID;
import static com.voting.RestaurantTestData.THIRD_RESTAURANT_ID;
import static com.voting.UserTestData.ADMIN_ID;
import static com.voting.UserTestData.USER_ID;
import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsComparator(Vote.class);
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class);

    public static final int NOT_FOUND = 10;
    public static final int USER_VOTE_ID = START_SEQ + 17;
    public static final int ADMIN_VOTE_ID = START_SEQ + 18;
    public static final Vote USER_VOTE = new Vote(USER_VOTE_ID, USER_ID, FIRST_RESTAURANT_ID, LocalDate.parse("2020-08-30"));

    public static final VoteTo USER_VOTE_TO = new VoteTo(USER_VOTE.getId(), USER_VOTE.getRestaurantFK(), USER_VOTE.getVoteDate());

    public static Vote getNew() {
        return new Vote(USER_ID, THIRD_RESTAURANT_ID, LocalDate.parse("2020-08-31"));
    }

    public static Vote getNewForReVote() {
        return new Vote(ADMIN_VOTE_ID, ADMIN_ID, THIRD_RESTAURANT_ID, LocalDate.parse("2020-08-31"));
    }

    public static Vote getVoteForLunchThatNotExist() {
        return new Vote(ADMIN_ID, FIRST_RESTAURANT_ID);
    }

    public static Vote getNewAdminVote() {
        return new Vote(ADMIN_ID, FIRST_RESTAURANT_ID, LocalDate.parse("2020-08-30"));
    }
}

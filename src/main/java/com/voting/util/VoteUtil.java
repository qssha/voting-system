package com.voting.util;

import com.voting.model.Vote;
import com.voting.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    public static List<VoteTo> votesToVoteTos(List<Vote> votes) {
        return votes.stream().map(x -> new VoteTo(x.getId(), x.getRestaurantFK(),
                x.getVoteDate())).collect(Collectors.toList());
    }
}

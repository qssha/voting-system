package com.voting.web.vote;

import com.voting.model.Vote;
import com.voting.service.LunchService;
import com.voting.service.UserService;
import com.voting.service.VoteService;
import com.voting.to.RestaurantTo;
import com.voting.to.VoteTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractVoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    public void vote(int id, int restaurantId, LocalDateTime dateTime) {
        log.info("user {} vote for Restaurant {} on date {}", id, restaurantId, dateTime.toString());
        voteService.vote(new Vote(id, restaurantId), dateTime);
    }

    public List<RestaurantTo> getRestaurants(LocalDate date) {
        log.info("get lunches for date {}", date.toString());
        return voteService.getAllWithLunchForDate(date);
    }

    public List<VoteTo> getAllVotes(int id) {
        log.info("get votes for user {}", id);
        return voteService.getAllVoteTos(id);
    }
}

package com.voting.web.vote;

import com.voting.to.RestaurantTo;
import com.voting.to.VoteTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController extends AbstractVoteController {

    private final Clock clock;

    public static final String REST_URL = "/rest/voting";

    public VoteRestController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/vote/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int restaurantId) {
        super.vote(authUserId(), restaurantId, LocalDateTime.now(clock));
    }

    @GetMapping("/votes")
    public List<VoteTo> getVotes() {
        return super.getAllVotes(authUserId());
    }

    @GetMapping("/restaurants")
    public List<RestaurantTo> getRestaurants() {
        return super.getRestaurants(LocalDate.now(clock));
    }
}

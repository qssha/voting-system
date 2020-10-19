package com.voting.web.vote;

import com.voting.RestaurantTestData;
import com.voting.TimeClockMock;
import com.voting.VoteTestData;
import com.voting.model.Vote;
import com.voting.service.VoteService;
import com.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static com.voting.RestaurantTestData.*;
import static com.voting.TestUtil.userHttpBasic;
import static com.voting.UserTestData.*;
import static com.voting.VoteTestData.*;
import static com.voting.util.exception.ErrorType.VOTE_ERROR;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + "/";

    @Autowired
    private TimeClockMock clock;

    @Autowired
    private VoteService voteService;

    @Test
    void getRestaurants() throws Exception {
        clock.setCurrentTime(VOTE_DATETIME);
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurants")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(List.of(getRestaurantTo())));
    }

    @Test
    void getVotes() throws Exception {
        clock.setCurrentTime(VOTE_DATETIME);
        perform(MockMvcRequestBuilders.get(REST_URL + "votes")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(VoteTestData.USER_VOTE_TO)));
    }

    @Test
    void vote() throws Exception {
        clock.setCurrentTime(VOTE_DATETIME);
        perform(MockMvcRequestBuilders.post(REST_URL + "vote/" + FIRST_RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote vote = voteService.getVote(ADMIN_ID, LocalDate.now(clock));
        Vote newAdminVote = getNewAdminVote();
        newAdminVote.setId(vote.getId());
        VOTE_MATCHER.assertMatch(vote, newAdminVote);
    }

    @Test
    void voteFailed() throws Exception {
        clock.setCurrentTime(VOTE_FAILED_DATETIME);
        perform(MockMvcRequestBuilders.post(REST_URL + "vote/" + FIRST_RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VOTE_ERROR))
                .andExpect(detailMessage(VOTE_FAILED_MESSAGE));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurants"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void voteNotFound() throws Exception {
        clock.setCurrentTime(VOTE_DATETIME);
        perform(MockMvcRequestBuilders.post(REST_URL + "vote/" + RestaurantTestData.NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}

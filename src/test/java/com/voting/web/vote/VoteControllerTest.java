package com.voting.web.vote;

import com.voting.VoteTestData;
import com.voting.service.UserService;
import com.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.voting.RestaurantTestData.*;
import static com.voting.VoteTestData.VOTE_MATCHER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + "/";

    @Autowired
    private UserService userService;

    @Test
    void getRestaurants() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurants"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(List.of(getRestaurantTo())));
    }

    @Test
    void getVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VoteTestData.USER_VOTE)));
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "vote/" + FIRST_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}

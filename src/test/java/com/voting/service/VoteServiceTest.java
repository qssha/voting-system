package com.voting.service;

import com.voting.VoteTestData;
import com.voting.model.Vote;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.voting.UserTestData.ADMIN_ID;
import static com.voting.UserTestData.USER_ID;
import static com.voting.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void getVoteByIdAndName() throws Exception {
        VOTE_MATCHER.assertMatch(voteService.getVote(USER_ID, LocalDate.of(2020, 8, 30)),
                USER_VOTE);
    }

    @Test
    void getVoteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> voteService.getVote(NOT_FOUND, LocalDate.of(2020, 8, 30)));
    }

    @Test
    void getAllVotesForDate() throws Exception {
        VOTE_MATCHER.assertMatch(voteService.getAllVotes(USER_ID), USER_VOTE);
    }

    @Test
    void vote() throws Exception {
        LocalDateTime voteTime = LocalDateTime.of(2020, 8, 31, 10, 0, 0);
        Vote created = VoteTestData.getNew();
        voteService.vote(created, voteTime);
        Vote newVote = voteService.getVote(USER_ID, voteTime.toLocalDate());
        created.setId(newVote.getId());
        VOTE_MATCHER.assertMatch(newVote, created);
    }

    @Test
    void reVote() throws Exception {
        LocalDateTime voteTime = LocalDateTime.of(2020, 8, 31, 10, 0, 0);
        Vote createdForReVote = VoteTestData.getNewForReVote();
        voteService.vote(createdForReVote, voteTime);
        Vote updatedVote = voteService.getVote(ADMIN_ID, voteTime.toLocalDate());
        createdForReVote.setId(updatedVote.getId());
        VOTE_MATCHER.assertMatch(updatedVote, createdForReVote);
    }

    @Test
    void voteTimeExpired() throws Exception {
        assertThrows(VoteException.class, () -> voteService.vote(getNewForReVote(),
                LocalDateTime.of(2020, 8, 31, 12, 0, 0)));
    }

    @Test
    void voteNoLunch() throws Exception {
        assertThrows(NotFoundException.class, () -> voteService.vote(VoteTestData.getVoteForLunchThatNotExist(),
                LocalDateTime.of(2020, 8, 31, 10, 0, 0)));
    }


    @Test
    void checkLunchForDate() throws Exception {
        assertTrue(voteService.checkLunchForDate(100000, LocalDate.of(2020, 8, 30)));
        assertFalse(voteService.checkLunchForDate(100000, LocalDate.of(2020, 8, 31)));
    }
}

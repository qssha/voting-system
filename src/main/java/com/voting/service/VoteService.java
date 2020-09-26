package com.voting.service;

import com.voting.model.Vote;
import com.voting.repository.VoteCrudRepository;
import com.voting.to.RestaurantTo;
import com.voting.to.VoteTo;
import com.voting.util.MenuUtil;
import com.voting.util.VoteUtil;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private static final LocalTime lunchTime = LocalTime.of(11, 0, 0);

    private final VoteCrudRepository voteCrudRepository;
    private final LunchService lunchService;

    public VoteService(VoteCrudRepository voteCrudRepository, LunchService lunchService) {
        this.voteCrudRepository = voteCrudRepository;
        this.lunchService = lunchService;
    }

    @Transactional
    public void vote(Vote vote, LocalDateTime voteDateTime) {
        voteWithEndTime(vote, voteDateTime, lunchTime);
    }

    @Transactional
    public void voteWithEndTime(Vote vote, LocalDateTime voteDateTime, LocalTime endOfVoteTime) {
        //checking lunch for this date by restaurant id, lunches will be cached
        if (!checkLunchForDate(vote.getRestaurantFK(), voteDateTime.toLocalDate())) {
            throw new NotFoundException("Restaurant with id=" + vote.getRestaurantFK()
                    + " does not offer lunch for date=" + voteDateTime.toLocalDate().toString());
        }

        Vote prevVote = voteCrudRepository.getByUserFKAndVoteDate(vote.getUserFK(), voteDateTime.toLocalDate());
        if (prevVote != null) {
            if (voteDateTime.toLocalTime().isAfter(endOfVoteTime)) {
                throw new VoteException("Can not vote after " + endOfVoteTime.toString());
            }
            vote.setId(prevVote.getId());
        }
        vote.setVoteDate(voteDateTime.toLocalDate());
        voteCrudRepository.save(vote);
    }

    public Vote getVote(int id, LocalDate date) {
        return checkNotFoundWithId(voteCrudRepository.getByUserFKAndVoteDate(id, date), id);
    }

    public List<Vote> getAllVotes(int id) {
        return voteCrudRepository.getAllByUserFK(id);
    }

    public boolean checkLunchForDate(int restaurantId, LocalDate date) {
        return lunchService.getByDate(date).stream().anyMatch(x -> x.getRestaurant().getId() == restaurantId);
    }

    public List<VoteTo> getAllVoteTos(int id) {
        return VoteUtil.votesToVoteTos(getAllVotes(id));
    }

    public List<RestaurantTo> getAllWithLunchForDate(LocalDate date) {
        return MenuUtil.LunchToRestaurantTo(lunchService.getByDate(date));
    }
}

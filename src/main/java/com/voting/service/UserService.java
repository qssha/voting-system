package com.voting.service;

import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.repository.UserCrudRepository;
import com.voting.repository.VoteCrudRepository;
import com.voting.util.exception.VoteException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFound;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserCrudRepository userCrudRepository;
    private final VoteCrudRepository voteCrudRepository;
    private final LunchService lunchService;

    public UserService(UserCrudRepository userCrudRepository, VoteCrudRepository voteCrudRepository, LunchService lunchService) {
        this.userCrudRepository = userCrudRepository;
        this.voteCrudRepository = voteCrudRepository;
        this.lunchService = lunchService;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userCrudRepository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(userCrudRepository.delete(id) != 0, id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userCrudRepository.findById(id).orElse(null), id);
    }

    public List<User> getAll() {
        return userCrudRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userCrudRepository.save(user), user.getId());
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userCrudRepository.getByEmail(email), "email=" + email);
    }

    public void vote(Vote vote, LocalDateTime voteDateTime) {
        voteWithEndTime(vote, voteDateTime, LocalTime.of(11, 0, 0));
    }

    @Transactional
    public void voteWithEndTime(Vote vote, LocalDateTime voteDateTime, LocalTime endOfVoteTime) {
        //checking lunch for this date by restaurant id, lunches will be cached
        lunchService.getByRestaurantIdAndDate(vote.getRestaurantFK(), voteDateTime.toLocalDate());

        if (voteDateTime.toLocalTime().isAfter(endOfVoteTime)) {
            throw new VoteException("Can't re-vote after " + endOfVoteTime.toString());
        } else {
            Vote prevVote = voteCrudRepository.getByUserFKAndVoteDate(vote.getUserFK(), voteDateTime.toLocalDate());
            if (prevVote != null) {
                vote.setId(prevVote.getId());
            }
            voteCrudRepository.save(vote);
        }
    }

    public Vote getVote(int id, LocalDate date) {
        return checkNotFoundWithId(voteCrudRepository.getByUserFKAndVoteDate(id, date), id);
    }

    public List<Vote> getAllVotes(int id) {
        return voteCrudRepository.getAllByUserFK(id);
    }
}

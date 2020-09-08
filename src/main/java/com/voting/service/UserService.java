package com.voting.service;

import com.voting.model.Lunch;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.repository.UserCrudRepository;
import com.voting.repository.VoteCrudRepository;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userCrudRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(userCrudRepository.delete(id) != 0, id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userCrudRepository.findById(id).orElse(null), id);
    }

    @Cacheable(value = "users")
    public List<User> getAll() {
        return userCrudRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @CacheEvict(value = "users", allEntries = true)
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
        if (!checkLunchForDate(vote.getRestaurantFK(), voteDateTime.toLocalDate())) {
            throw new NotFoundException("Restaurant with id=" + vote.getRestaurantFK()
                    + " doesn't offer lunch for date=" + voteDateTime.toLocalDate().toString());
        }

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

    private boolean checkLunchForDate(int restaurantId, LocalDate date) {
        return lunchService.getByDate(date).stream().anyMatch(x -> x.getRestaurant().getId() == restaurantId);
    }
}

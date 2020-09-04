package com.voting.service;

import com.voting.model.Lunch;
import com.voting.model.User;
import com.voting.repository.UserCrudRepository;
import com.voting.util.exception.VoteException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFound;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserCrudRepository userCrudRepository;
    private final LunchService lunchService;

    public UserService(UserCrudRepository userCrudRepository, LunchService lunchService) {
        this.userCrudRepository = userCrudRepository;
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

    public User getWithLunch(int id) {
        return checkNotFoundWithId(userCrudRepository.getWithLunch(id), id);
    }

    public void vote(int id, Lunch lunch, LocalDateTime voteDateTime) {
        voteWithEndTime(id, lunch, voteDateTime, LocalTime.of(11, 0, 0));
    }

    @Transactional
    public void voteWithEndTime(int id, Lunch lunch, LocalDateTime voteDateTime, LocalTime endOfVoteTime) {
        User currentUser = getWithLunch(id);
        Lunch userLunch = currentUser.getLunch();
        LocalDateTime lastVoteDateTime = currentUser.getLastVoteDateTime();

        if (userLunch != null && lastVoteDateTime != null
                && lastVoteDateTime.toLocalDate().equals(voteDateTime.toLocalDate())) {
            if (lastVoteDateTime.toLocalTime().isAfter(endOfVoteTime)) {
                throw new VoteException("Can't re-vote after " + endOfVoteTime.toString());
            } else {
                Lunch oldVotedLunch = currentUser.getLunch();
                oldVotedLunch.decrementRating();
                lunchService.update(oldVotedLunch);
            }
        }

        if (lunch != null) {
            lunch.incrementRating();
            currentUser.setLunch(lunch);
            currentUser.setLastVoteDateTime(voteDateTime);
            update(currentUser);
        }
    }
}

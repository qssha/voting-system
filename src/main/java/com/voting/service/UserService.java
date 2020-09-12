package com.voting.service;

import com.voting.AuthorizedUser;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.repository.UserCrudRepository;
import com.voting.repository.VoteCrudRepository;
import com.voting.to.UserTo;
import com.voting.util.UserUtil;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.voting.util.UserUtil.prepareToSave;
import static com.voting.util.ValidationUtil.checkNotFound;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserCrudRepository userCrudRepository;
    private final VoteCrudRepository voteCrudRepository;
    private final LunchService lunchService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserCrudRepository userCrudRepository, VoteCrudRepository voteCrudRepository, LunchService lunchService, PasswordEncoder passwordEncoder) {
        this.userCrudRepository = userCrudRepository;
        this.voteCrudRepository = voteCrudRepository;
        this.lunchService = lunchService;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
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
        checkNotFoundWithId(prepareAndSave(user), user.getId());
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
                    + " does not offer lunch for date=" + voteDateTime.toLocalDate().toString());
        }

        if (voteDateTime.toLocalTime().isAfter(endOfVoteTime)) {
            throw new VoteException("Can not vote after " + endOfVoteTime.toString());
        } else {
            Vote prevVote = voteCrudRepository.getByUserFKAndVoteDate(vote.getUserFK(), voteDateTime.toLocalDate());
            if (prevVote != null) {
                vote.setId(prevVote.getId());
            }
            vote.setVoteDate(voteDateTime.toLocalDate());
            voteCrudRepository.save(vote);
        }
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

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        UserUtil.updateFromTo(user, userTo);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userCrudRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return userCrudRepository.save(prepareToSave(user, passwordEncoder));
    }
}

package com.voting.service;

import com.voting.AuthorizedUser;
import com.voting.model.User;
import com.voting.repository.UserCrudRepository;
import com.voting.repository.VoteCrudRepository;
import com.voting.to.UserTo;
import com.voting.util.UserUtil;
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

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
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
        checkNotFoundWithId(prepareAndSave(user), user.getId());
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userCrudRepository.getByEmail(email), "email=" + email);
    }

    @Transactional
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

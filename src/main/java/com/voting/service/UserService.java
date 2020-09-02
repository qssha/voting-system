package com.voting.service;

import com.voting.model.User;
import com.voting.repository.UserCrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static com.voting.util.ValidationUtil.checkNotFound;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserCrudRepository userCrudRepository;

    public UserService(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
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
}

package com.voting.service;

import com.voting.model.User;
import com.voting.repository.UserCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserCrudRepository userCrudRepository;

    public UserService(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    public User create(User user) {
        return userCrudRepository.save(user);
    }

    public void delete(int id) {
        userCrudRepository.deleteById(id);
    }

    public User get(int id) {
        return userCrudRepository.getOne(id);
    }

    public List<User> getAll() {
        return userCrudRepository.findAll();
    }

    public void update(User user) {
        userCrudRepository.save(user);
    }

    public User getByEmail(String email) {
        return userCrudRepository.getByEmail(email);
    }
}

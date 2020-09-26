package com.voting.service;

import com.voting.VoteTestData;
import com.voting.model.Role;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.voting.UserTestData.NOT_FOUND;
import static com.voting.UserTestData.getNew;
import static com.voting.UserTestData.*;
import static com.voting.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void delete() throws Exception {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    void create() throws Exception {
        User created = userService.create(getNew());
        int newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }


    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> userService.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        User user = userService.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> userService.get(NOT_FOUND));
    }

    @Test
    void getByEmail() throws Exception {
        User user = userService.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        userService.update(updated);
        USER_MATCHER.assertMatch(userService.get(USER_ID), getUpdated());
    }

    @Test
    void getAll() throws Exception {
        List<User> all = userService.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> userService.create(new User(null, "", "mail@yandex.ru",
                "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "",
                "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru",
                "", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "U", "mail@yandex.ru",
                "password", Role.USER)), ConstraintViolationException.class);
        ;
        validateRootCause(() -> userService.create(new User(null, "User", "mail.ru",
                "password", Role.USER)), ConstraintViolationException.class);
    }
}

package com.voting.service;

import com.voting.model.Lunch;
import com.voting.model.Role;
import com.voting.model.User;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

import static com.voting.LunchTestData.FIRST_LUNCH;
import static com.voting.LunchTestData.SECOND_LUNCH;
import static com.voting.LunchTestData.THIRD_LUNCH;
import static com.voting.LunchTestData.LUNCH_MATCHER;
import static com.voting.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void getWithLunch() throws Exception {
        User user = userService.getWithLunch(USER_ID);
        Lunch lunch = user.getLunch();
        LUNCH_MATCHER.assertMatch(lunch, FIRST_LUNCH);
    }

    @Test
    void voteExistingUser() throws Exception {
        userService.vote(USER_ID, THIRD_LUNCH,
                LocalDateTime.of(2020, 9, 1, 15, 0, 0));
        LUNCH_MATCHER.assertMatch(userService.getWithLunch(USER_ID).getLunch(), THIRD_LUNCH);
        THIRD_LUNCH.decrementRating();
    }

    @Test
    void voteNewUser() throws Exception {
        User newUser = userService.create(getNew());
        int newId = newUser.getId();
        userService.vote(newId, FIRST_LUNCH,
                LocalDateTime.of(2020, 8, 30, 10, 0, 0));
        LUNCH_MATCHER.assertMatch(userService.getWithLunch(newId).getLunch(), FIRST_LUNCH);
        FIRST_LUNCH.decrementRating();
    }

    @Test
    void reVote() throws Exception {
        userService.vote(ADMIN_ID, THIRD_LUNCH,
                LocalDateTime.of(2020, 8, 31, 10, 0, 0));
        LUNCH_MATCHER.assertMatch(userService.getWithLunch(ADMIN_ID).getLunch(), THIRD_LUNCH);
        SECOND_LUNCH.decrementRating();
        THIRD_LUNCH.incrementRating();
    }

    @Test
    void reVoteFailed() throws Exception {
        assertThrows(VoteException.class, () -> userService.vote(USER_ID, FIRST_LUNCH,
                LocalDateTime.of(2020, 8, 30, 12, 0, 0)));
    }
}

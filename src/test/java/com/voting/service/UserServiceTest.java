package com.voting.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.UserTestData.USER_ID;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void delete() throws Exception {
        userService.delete(USER_ID);
        Assertions.assertFalse(userService.get(USER_ID).isPresent());
    }
}

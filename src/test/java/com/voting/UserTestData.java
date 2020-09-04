package com.voting;

import com.voting.model.Role;
import com.voting.model.User;

import java.util.Collections;
import java.util.Date;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "lunch", "lastVoteDateTime");

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ + 15;
    public static final int ADMIN_ID = START_SEQ + 16;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}

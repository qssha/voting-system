package com.voting.util;

import com.voting.model.User;
import com.voting.to.UserTo;

public class UserUtil {
    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}

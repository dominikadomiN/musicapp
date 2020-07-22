package com.dominika.commons;

import com.dominika.commons.model.User;

public interface UserRepository {
    long addUser(User newUser);

    void deleteUser(long userId);
}

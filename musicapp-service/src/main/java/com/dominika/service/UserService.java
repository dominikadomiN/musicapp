package com.dominika.service;

import com.dominika.commons.model.User;

public interface UserService {

    long addUser(User user);

    void deleteUserById(long id);
}

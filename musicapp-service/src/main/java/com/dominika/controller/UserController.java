package com.dominika.controller;

import com.dominika.commons.model.User;
import com.dominika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add")
    public long addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteSongById(@PathVariable long id){
        userService.deleteUserById(id);
    }
}

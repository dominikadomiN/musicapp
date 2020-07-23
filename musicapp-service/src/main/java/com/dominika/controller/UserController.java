package com.dominika.controller;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;
import com.dominika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add")
    public long addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(value = "/show/{userId}/{playlistId}")
    public Optional<Playlist> showPlaylistById(@PathVariable long userId,
                                               @PathVariable long playlistId) {
        return userService.getPlaylist(userId, playlistId);
    }

    @GetMapping(value = "/show/{userId}")
    public List<Playlist> showAllPlaylists(@PathVariable long userId) {
        return userService.getAllPlaylists(userId);
    }
}

package com.dominika.service;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    long addUser(User user);

    void deleteUserById(long id);

    List<Playlist> getAllPlaylists(long userId);

    Optional<Playlist> getPlaylist(long userId, long playlistId);
}

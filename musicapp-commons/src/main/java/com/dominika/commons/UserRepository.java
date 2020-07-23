package com.dominika.commons;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    long addUser(User newUser);

    void deleteUser(long userId);

    List<Playlist> getAllPlaylists(long userId);

    Optional<Playlist> getPlaylist(long userId, long playlistId);
}

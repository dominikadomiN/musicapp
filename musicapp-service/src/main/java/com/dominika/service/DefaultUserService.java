package com.dominika.service;

import com.dominika.commons.UserRepository;
import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPlaylistService.class);
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteUser(id);
        LOGGER.info("UserEntity with id {} deleted", id);
    }

    @Override
    public List<Playlist> getAllPlaylists(long userId) {
        return userRepository.getAllPlaylists(userId);
    }

    @Override
    public Optional<Playlist> getPlaylist(long userId, long playlistId) {
        return userRepository.getPlaylist(userId, playlistId);
    }
}

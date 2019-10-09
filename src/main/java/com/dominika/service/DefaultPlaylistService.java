package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlaylistService implements PlaylistService {

    private PlaylistRepository playlistRepository;

    @Autowired
    public DefaultPlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public long addPlaylist(Playlist playlist) {
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return savedPlaylist.getId();
    }
}
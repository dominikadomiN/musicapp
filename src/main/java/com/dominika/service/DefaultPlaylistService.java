package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

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

    @Override
    public Playlist findPlaylistById(long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no such playlist with id = " + id));
    }

    @Override
    public void deletePlaylistById(long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public List<Playlist> getPlaylists() {
        List<Playlist> allPlaylists = playlistRepository.findAll();
        return allPlaylists;
    }
}

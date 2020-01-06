package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.model.Song;
import com.dominika.repository.PlaylistRepository;
import com.dominika.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class DefaultPlaylistService implements PlaylistService {

    private final PlaylistRepository playlistRepository;

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
        return playlistRepository.findAll();
    }

    @Override
    public void addSongsToPlaylist(long id, List<Song> songs) {
        Playlist playlist = findPlaylistById(id);
        playlist.setSongs(songs);
        playlistRepository.save(playlist);
    }
}

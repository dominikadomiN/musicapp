package com.dominika.service;

import com.dominika.entity.Playlist;
import com.dominika.entity.Song;
import com.dominika.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPlaylistService implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public DefaultPlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public long addPlaylist(Playlist playlist) {
        var savedPlaylist = playlistRepository.save(playlist);
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
        var playlist = findPlaylistById(id);
        playlist.setSongs(songs);
        playlistRepository.save(playlist);
    }
}

package com.dominika.service;

import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.entity.Playlist;
import com.dominika.entity.Song;
import com.dominika.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(NoSuchPlaylistException::new);
    }

    @Override
    public void deletePlaylistById(long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public List<Playlist> getPlaylists(String name) {
        if (name == null) {
            return playlistRepository.findAll();
        }
        return playlistRepository.findByName(name)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void addSongsToPlaylist(long id, List<Song> songs) {
        var playlist = findPlaylistById(id);
        playlist.setSongs(songs);
        playlistRepository.save(playlist);
    }
}

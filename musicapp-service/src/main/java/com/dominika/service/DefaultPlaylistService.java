package com.dominika.service;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.Song;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.commons.PlaylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultPlaylistService implements PlaylistService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPlaylistService.class);
    private final PlaylistRepository playlistRepository;

    @Autowired
    public DefaultPlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public long addPlaylist(Playlist playlist) {
        return playlistRepository.addPlaylist(playlist);
    }

    @Override
    public Playlist findPlaylistById(long id) {
        Optional<Playlist> maybePlaylist = playlistRepository.getPlaylist(id);
        if (maybePlaylist.isPresent()) {
            Playlist playlist = maybePlaylist.orElseThrow();
            LOGGER.info("PlaylistEntity with id {} found: {}", id, playlist);
            return playlist;
        }
        LOGGER.warn("PlaylistEntity with id {} not found", id);
        throw new NoSuchPlaylistException();
    }

    @Override
    public void deletePlaylistById(long id) {
        playlistRepository.deletePlaylist(id);
        LOGGER.info("PlaylistEntity with id {} deleted", id);
    }

    @Override
    public List<Playlist> getPlaylists(String name) {
        if (name == null) {
            var playlistsFound = playlistRepository.getAllPlaylists();
            LOGGER.info("Found {} playlists", playlistsFound.size());
            return playlistsFound;
        }
        List<Playlist> foundPlaylists = playlistRepository.findPlaylistsByName(name);
        LOGGER.info("Found {} playlists with name {} ", foundPlaylists.size(), name);
        return foundPlaylists;
    }

    @Override
    public void addSongsToPlaylist(long id, List<Song> songs) {
        var playlist = findPlaylistById(id);
        playlist.setSongs(songs);
        List<Long> songsId = songs.stream().map(Song::getId).collect(Collectors.toList());
        playlistRepository.addPlaylist(playlist);
        LOGGER.info("Songs with ids {} added to playlist with id {}", songsId, id);
    }
}

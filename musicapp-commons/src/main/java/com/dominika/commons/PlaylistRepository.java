package com.dominika.commons;

import com.dominika.commons.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {
    long addPlaylist(Playlist playlist);

    Optional<Playlist> getPlaylist(long id);

    void deletePlaylist(long id);

    List<Playlist> getAllPlaylists();

    List<Playlist> findPlaylistsByName(String name);
}

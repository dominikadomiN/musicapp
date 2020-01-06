package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.model.Song;

import java.util.List;

public interface PlaylistService {

    long addPlaylist(Playlist playlist);

    Playlist findPlaylistById(long id);

    void deletePlaylistById(long id);

    List<Playlist> getPlaylists();

    void addSongsToPlaylist(long id, List<Song> songs);
}

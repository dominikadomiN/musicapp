package com.dominika.service;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.Song;

import java.util.List;

public interface PlaylistService {

    long addPlaylist(Playlist playlist);

    Playlist findPlaylistById(long id);

    void deletePlaylistById(long id);

    List<Playlist> getPlaylists(String name);

    void addSongsToPlaylist(long id, List<Song> songs);
}

package com.dominika.service;

import com.dominika.model.Song;

import java.util.List;

public interface SongService {

    long addSong(Song song);
    List<Song> getSongs();
    Song findSongById(long id);
}

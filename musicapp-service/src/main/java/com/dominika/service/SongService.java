package com.dominika.service;

import com.dominika.entity.Song;

import java.util.List;

public interface SongService {

    long addSong(Song song);

    List<Song> getSongs(String name, String interpreter, String album, String genre, Integer year);

    Song findSongById(long id);

    void deleteSongById(long id);

}

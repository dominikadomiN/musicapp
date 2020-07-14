package com.dominika.service;

import com.dominika.commons.model.Song;
import com.dominika.controller.request.SongRequestParams;

import java.util.List;

public interface SongService {

    long addSong(Song song);

    List<Song> getSongs(SongRequestParams songRequestParams);

    Song findSongById(long id);

    void deleteSongById(long id);

}

package com.dominika.commons;

import com.dominika.commons.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongRepository {
    long saveSong(Song newSong);

    void deleteSong(long songId);

    Optional<Song> getSong(long id);

    List<Song> getAllSongs();
}

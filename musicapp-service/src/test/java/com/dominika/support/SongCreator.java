package com.dominika.support;

import com.dominika.entity.Song;

public class SongCreator {

    public static Song createSong(long id, String name, String interpreter, String album, String genre, int year) {
        Song song = createSong(name, interpreter, album, genre, year);
        song.setId(id);

        return song;
    }

    public static Song createSong(String name, String interpreter, String album, String genre, int year) {
        Song song = new Song();
        song.setName(name);
        song.setInterpreter(interpreter);
        song.setAlbum(album);
        song.setGenre(genre);
        song.setYear(year);

        return song;
    }
}

package com.dominika.repository.mapper;

import com.dominika.commons.model.Song;
import com.dominika.repository.entity.SongEntity;

public final class SongMapper {
    private SongMapper() {
    }

    public static SongEntity mapToSongEntity(Song newSong) {
        return SongEntity.builder()
                .name(newSong.getName())
                .album(newSong.getAlbum())
                .interpreter(newSong.getInterpreter())
                .genre(newSong.getGenre())
                .year(newSong.getYear())
                .build();
    }

    public static Song mapToSong(SongEntity newSong) {
        return Song.builder()
                .name(newSong.getName())
                .album(newSong.getAlbum())
                .interpreter(newSong.getInterpreter())
                .genre(newSong.getGenre())
                .year(newSong.getYear())
                .build();
    }
}

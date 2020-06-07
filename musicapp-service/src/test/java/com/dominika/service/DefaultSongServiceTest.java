package com.dominika.service;

import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.entity.Song;
import com.dominika.repository.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.dominika.support.SongCreator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DefaultSongServiceTest {
    private static final Song SONG_ONE = SongCreator.createSong("All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_ONE_WITH_ID = SongCreator.createSong(1L, "All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_TWO_WITH_ID = SongCreator.createSong(2L, "Sorry", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_THREE_WITH_ID = SongCreator.createSong(3L, "Better Off", "Ariana Grande", "Next", "Pop", 2019);
    private static final Song SONG_FOUR_WITH_ID = SongCreator.createSong(4L, "All Night", "Ariana Grande", "Next", "Pop", 2019);
    private static final Song SONG_FIVE_WITH_ID = SongCreator.createSong(5L, "Hello", "Travis", "123", "Rap", 2017);

    private DefaultSongService defaultSongService;
    private SongRepository songRepositoryMock;

    @BeforeEach
    void setUp() {
        this.songRepositoryMock = Mockito.mock(SongRepository.class);
        this.defaultSongService = new DefaultSongService(songRepositoryMock);
    }

    @Test
    void shouldAddSong() {
        //given
        when(songRepositoryMock.save(SONG_ONE)).thenReturn(SONG_ONE_WITH_ID);

        //when
        var actual = defaultSongService.addSong(SONG_ONE);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).save(SONG_ONE),
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldGetSongs_WithoutParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, null, null, null, null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_NameParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs("All Night", null, null, null, null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_InterpreterParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        var expected = List.of(SONG_THREE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, "Ariana Grande", null, null, null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_AlbumParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID));
        var expected = List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, null, "Next", null, null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_GenreParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, null, null, "Rap", null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_YearParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, null, null, null, 2017);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_InterpreterAndAlbumParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, "Beyonce", "Lemonade", null, null);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_GenreAndYearParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(null, null, null, "Rap", 2017);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }


    @Test
    void findSongById_shouldReturnSong() {
        //given
        when(songRepositoryMock.findById(1L)).thenReturn(Optional.of(SONG_ONE_WITH_ID));

        //when
        var actual = defaultSongService.findSongById(1L);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).findById(1L),
                () -> assertEquals(SONG_ONE_WITH_ID, actual)
        );
    }

    @Test
    void findSongById_shouldThrowNoSuchPlaylistException() {
        //given
        when(songRepositoryMock.findById(3L)).thenReturn(Optional.empty());

        //when
        assertThrows(NoSuchPlaylistException.class, () -> defaultSongService.findSongById(3L));
    }

    @Test
    void deleteSongById_SoundDeleteSong() {
        //when
        defaultSongService.deleteSongById(2L);

        //then
        Mockito.verify(songRepositoryMock).deleteById(2L);
    }
}
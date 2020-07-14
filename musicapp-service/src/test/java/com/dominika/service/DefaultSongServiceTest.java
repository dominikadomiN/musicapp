package com.dominika.service;

import com.dominika.commons.model.Song;
import com.dominika.controller.request.SongRequestParams;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.commons.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DefaultSongServiceTest {
    private static final Song SONG_ONE = Song.builder()
            .name("All Night")
            .interpreter("Beyonce")
            .album("Lemonade")
            .genre("Pop")
            .year(2016)
            .build();
    private static final Song SONG_ONE_WITH_ID = Song.builder()
            .id(1L)
            .name("All Night")
            .interpreter("Beyonce")
            .album("Lemonade")
            .genre("Pop")
            .year(2016)
            .build();

    private static final Song SONG_TWO_WITH_ID = Song.builder()
            .id(2L)
            .name("Sorry")
            .interpreter("Beyonce")
            .album("Lemonade")
            .genre("Pop")
            .year(2016)
            .build();
    private static final Song SONG_THREE_WITH_ID = Song.builder()
            .id(3L)
            .name("Better Off")
            .interpreter("Ariana Grande")
            .album("Next")
            .genre("Pop")
            .year(2019)
            .build();
    private static final Song SONG_FOUR_WITH_ID = Song.builder()
            .id(4L)
            .name("All Night")
            .interpreter("Ariana Grande")
            .album("Next")
            .genre("Pop")
            .year(2019)
            .build();
    private static final Song SONG_FIVE_WITH_ID = Song.builder()
            .id(5L)
            .name("Hello")
            .interpreter("Travis")
            .album("123")
            .genre("Rap")
            .year(2017)
            .build();
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_NAME =
            new SongRequestParams("All Night", null, null, null, null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_INTERPRETER =
            new SongRequestParams(null, "Ariana Grande", null, null, null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_ALBUM =
            new SongRequestParams(null, null, "Next", null, null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_GENRE =
            new SongRequestParams(null, null, null, "Rap", null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_YEAR =
            new SongRequestParams(null, null, null, null, 2017);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_NULL =
            new SongRequestParams(null, null, null, null, null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_INTERPRETER_AND_ALBUM =
            new SongRequestParams(null, "Beyonce", "Lemonade", null, null);
    private static final SongRequestParams SONG_REQUEST_PARAMS_WITH_GENRE_AND_YEAR =
            new SongRequestParams(null, null, null, "Rap", 2017);

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
        when(songRepositoryMock.saveSong(SONG_ONE)).thenReturn(SONG_ONE_WITH_ID.getId());

        //when
        var actual = defaultSongService.addSong(SONG_ONE);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).saveSong(SONG_ONE),
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldGetSongs_WithoutParams() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_NULL);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_NameParam() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_NAME);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_InterpreterParam() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        var expected = List.of(SONG_THREE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_INTERPRETER);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_AlbumParam() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID));
        var expected = List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_ALBUM);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_GenreParam() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_GENRE);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_YearParam() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_YEAR);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_InterpreterAndAlbumParams() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        var expected = List.of(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_INTERPRETER_AND_ALBUM);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetSongs_GenreAndYearParams() {
        //given
        when(songRepositoryMock.getAllSongs()).thenReturn(List.of(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        var expected = List.of(SONG_FIVE_WITH_ID);

        //when
        var actual = defaultSongService.getSongs(SONG_REQUEST_PARAMS_WITH_GENRE_AND_YEAR);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getAllSongs(),
                () -> assertEquals(expected, actual)
        );
    }


    @Test
    void findSongById_shouldReturnSong() {
        //given
        when(songRepositoryMock.getSong(1L)).thenReturn(Optional.of(SONG_ONE_WITH_ID));

        //when
        var actual = defaultSongService.findSongById(1L);

        //then
        assertAll(
                () -> Mockito.verify(songRepositoryMock).getSong(1L),
                () -> assertEquals(SONG_ONE_WITH_ID, actual)
        );
    }

    @Test
    void findSongById_shouldThrowNoSuchPlaylistException() {
        //given
        when(songRepositoryMock.getSong(3L)).thenReturn(Optional.empty());

        //when
        assertThrows(NoSuchPlaylistException.class, () -> defaultSongService.findSongById(3L));
    }

    @Test
    void deleteSongById_SoundDeleteSong() {
        //when
        defaultSongService.deleteSongById(2L);

        //then
        Mockito.verify(songRepositoryMock).deleteSong(2L);
    }
}
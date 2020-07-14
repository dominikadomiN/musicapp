package com.dominika.controller;

import com.dominika.commons.model.Song;
import com.dominika.controller.request.SongRequestParams;
import com.dominika.controller.response.SongsResponse;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.service.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class SongControllerTest {
    private static final long SONG_ONE_ID = 1L;
    private static final Song SONG_ONE = Song.builder()
            .id(SONG_ONE_ID)
            .name("Hello")
            .album("1")
            .interpreter("Madonna")
            .year(1998)
            .genre("pop")
            .build();
    private static final SongRequestParams SONG_REQUEST_PARAMS_ONE =
            new SongRequestParams("Hello", "Madonna", "1", "pop", 1998);

    private SongService songServiceMock;
    private SongController songController;

    @BeforeEach
    void setUp() {
        this.songServiceMock = Mockito.mock(SongService.class);
        this.songController = new SongController(songServiceMock);
    }

    @Test
    void shouldAddSong() {
        //given
        when(songServiceMock.addSong(SONG_ONE)).thenReturn(1L);

        //when
        var actual = songController.addSong(SONG_ONE);

        //then
        assertAll(
                () -> Mockito.verify(songServiceMock).addSong(SONG_ONE),
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldDeleteSong() {
        //given,when
        songController.deleteSongById(SONG_ONE_ID);

        //then
        Mockito.verify(songServiceMock).deleteSongById(SONG_ONE_ID);
    }

    @Test
    void shouldShowSongById() {
        //given
        when(songServiceMock.findSongById(SONG_ONE_ID)).thenReturn(SONG_ONE);

        //when
        var actual = songController.showSongById(SONG_ONE_ID);

        //then
        assertAll(
                () -> Mockito.verify(songServiceMock).findSongById(SONG_ONE_ID),
                () -> assertEquals(SONG_ONE, actual)
        );
    }

    @Test
    void shouldThrowNoSuchPlaylistException_whenThereIsNoSongWithThatId() {
        //given
        doThrow(NoSuchPlaylistException.class).when(songServiceMock).findSongById(2L);

        //when,then
        assertThrows(NoSuchPlaylistException.class, () -> songController.showSongById(2L));
    }

    @Test
    void shouldShowSongs() {
        var songs = Collections.singletonList(SONG_ONE);
        var expected = new SongsResponse();
        expected.setSongs(songs);
        expected.setTotal(1);

        when(songServiceMock.getSongs(SONG_REQUEST_PARAMS_ONE)).thenReturn(songs);

        //when
        var actual = songController.showSongs(SONG_REQUEST_PARAMS_ONE);

        //then
        assertAll(
                () -> Mockito.verify(songServiceMock).getSongs(SONG_REQUEST_PARAMS_ONE),
                () -> assertEquals(expected, actual)
        );
    }
}
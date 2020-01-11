package com.dominika.controller;

import com.dominika.model.Song;
import com.dominika.model.SongsResponse;
import com.dominika.service.SongService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import support.SongCreator;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SongControllerTest {
    private static final long SONG_ONE_ID = 1L;
    private static final Song SONG_ONE = SongCreator.createSong(SONG_ONE_ID, "Hello", "Madonna", "1",
            "pop", 1998);

    private SongService songServiceMock;
    private SongController songController;

    @Before
    public void setUp() {
        this.songServiceMock = Mockito.mock(SongService.class);
        this.songController = new SongController(songServiceMock);
    }

    @Test
    public void shouldAddSong() {
        //given
        when(songServiceMock.addSong(SONG_ONE)).thenReturn(1L);

        //when
        long actual = songController.addSong(SONG_ONE);

        //then
        Mockito.verify(songServiceMock).addSong(SONG_ONE);
        assertEquals(1L, actual);
    }

    @Test
    public void shouldDeleteSong() {
        //given,when
        songController.deleteSongById(SONG_ONE_ID);

        //then
        Mockito.verify(songServiceMock).deleteSongById(SONG_ONE_ID);
    }

    @Test
    public void shouldShowSongById() {
        //given
        when(songServiceMock.findSongById(SONG_ONE_ID)).thenReturn(SONG_ONE);

        //when
        Song actual = songController.showSongById(SONG_ONE_ID);

        //then
        Mockito.verify(songServiceMock).findSongById(SONG_ONE_ID);
        assertEquals(SONG_ONE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeException_whenThereIsNoSongWithThatId() {
        //given
        doThrow(RuntimeException.class).when(songServiceMock).findSongById(2L);

        //when,then
        songController.showSongById(2L);
    }

    @Test
    public void shouldShowSongs() {
        List<Song> songs = Collections.singletonList(SONG_ONE);
        SongsResponse expected = new SongsResponse();
        expected.setSongs(songs);
        expected.setTotal(1);

        when(songServiceMock.getSongs("Hello", "Madonna", "1", "pop", 1998)).thenReturn(songs);

        //when
        SongsResponse actual = songController.showSongs("Hello", "Madonna", "1", "pop", 1998);

        //then
        Mockito.verify(songServiceMock).getSongs("Hello", "Madonna", "1", "pop", 1998);
        assertEquals(expected, actual);
    }
}
package com.dominika.controller;

import com.dominika.model.Playlist;
import com.dominika.model.PlaylistResponse;
import com.dominika.model.Song;
import com.dominika.service.PlaylistService;
import com.dominika.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import support.PlaylistCreator;
import support.SongCreator;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class PlaylistControllerTest {

    private static final Playlist PLAYLIST_ONE = PlaylistCreator.createPlaylist("Chill");
    private static final Playlist PLAYLIST_ONE_WITH_ID = PlaylistCreator.createPlaylist(10L, "Chill");
    private static final long SONG_ONE_ID = 1L;
    private static final Song SONG_ONE = SongCreator.createSong(SONG_ONE_ID, "Hello", "Madonna", "1",
            "pop", 1998);
    private static final long SONG_TWO_ID = 2L;
    private static final Song SONG_TWO = SongCreator.createSong(SONG_TWO_ID, "Good", "Don", "Nice",
            "pop", 2014);

    private PlaylistService playlistServiceMock;
    private SongService songServiceMock;
    private PlaylistController playlistController;

    @BeforeEach
    void setUp() {
        this.playlistServiceMock = Mockito.mock(PlaylistService.class);
        this.songServiceMock = Mockito.mock(SongService.class);
        this.playlistController = new PlaylistController(playlistServiceMock, songServiceMock);
    }

    @Test
    void shouldAddPlaylist() {
        //given
        when(playlistServiceMock.addPlaylist(PLAYLIST_ONE)).thenReturn(10L);

        //when
        long actual = playlistController.addPlaylist(PLAYLIST_ONE);

        //then
        assertEquals(10L, actual);
    }

    @Test
    void shouldShowPlaylistById() {
        //given
        when(playlistServiceMock.findPlaylistById(10L)).thenReturn(PLAYLIST_ONE_WITH_ID);

        //when
        Playlist actual = playlistController.showPlaylistById(10L);

        //then
        assertEquals(PLAYLIST_ONE_WITH_ID, actual);
    }

    @Test
    void shouldThrowRuntimeException_whenThereIsNoPlaylistById() {
        //given
        doThrow(RuntimeException.class).when(playlistServiceMock).findPlaylistById(30L);

        //when,then
        assertThrows(RuntimeException.class, () -> playlistController.showPlaylistById(30L));
    }

    @Test
    void shouldAddSongsToPlaylist() {
        //given
        List<Long> songsId = List.of(SONG_ONE_ID, SONG_TWO_ID);
        List<Song> songs = List.of(SONG_ONE, SONG_TWO);

        Playlist playlist = new Playlist();
        playlist.setId(10L);
        playlist.setName("Chill");
        playlist.setSongs(songs);

        when(songServiceMock.findSongById(SONG_ONE_ID)).thenReturn(SONG_ONE);
        when(songServiceMock.findSongById(SONG_TWO_ID)).thenReturn(SONG_TWO);

        //when
        playlistController.addSongsToPlaylist(10L, songsId);

        //then
        assertAll(
                () -> Mockito.verify(playlistServiceMock).addSongsToPlaylist(10L, songs),
                () -> Mockito.verify(songServiceMock, times(2)).findSongById(anyLong())
        );
    }

    @Test
    void shouldDeletePlaylistById() {
        //given,when
        playlistController.deletePlaylistById(10L);

        //then
        Mockito.verify(playlistServiceMock).deletePlaylistById(10L);
    }

    @Test
    void shouldShowPlaylist() {
        //given
        List<Playlist> playlists = Collections.singletonList(PLAYLIST_ONE_WITH_ID);
        PlaylistResponse expected = new PlaylistResponse();
        expected.setPlaylists(playlists);
        expected.setTotal(1);

        when(playlistServiceMock.getPlaylists()).thenReturn(playlists);

        //when
        PlaylistResponse actual = playlistController.showPlaylist("Chill");

        //then
        assertAll(
                () -> Mockito.verify(playlistServiceMock).getPlaylists(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldThrowRuntimeException_whenPlaylistIdNotFound() {
        //given
        List<Long> songsId = Collections.singletonList(SONG_ONE_ID);
        List<Song> songs = Collections.singletonList(SONG_ONE);
        when(songServiceMock.findSongById(SONG_ONE_ID)).thenReturn(SONG_ONE);

        doThrow(RuntimeException.class).when(playlistServiceMock).addSongsToPlaylist(20L, songs);

        //when
        assertThrows(RuntimeException.class, () -> playlistController.addSongsToPlaylist(20L, songsId));

        //then
        Mockito.verify(songServiceMock).findSongById(SONG_ONE_ID);
    }

    @Test
    void shouldThrowRuntimeException_whenSongIdNotFound() {
        //given
        List<Long> songsId = Collections.singletonList(SONG_ONE_ID);
        doThrow(RuntimeException.class).when(songServiceMock).findSongById(SONG_ONE_ID);

        //when,then
        assertThrows(RuntimeException.class, () -> playlistController.addSongsToPlaylist(20L, songsId));
    }
}
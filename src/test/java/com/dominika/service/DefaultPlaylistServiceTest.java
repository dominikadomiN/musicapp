package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.model.Song;
import com.dominika.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import support.PlaylistCreator;
import support.SongCreator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DefaultPlaylistServiceTest {

    private static final Playlist PLAYLIST_ONE = PlaylistCreator.createPlaylist("Weekend");
    private static final Playlist PLAYLIST_ONE_WITH_ID = PlaylistCreator.createPlaylist(1L, "Weekend");
    private static final Song SONG_ONE = SongCreator.createSong("Hello", "Beyonce", "4x4",
            "pop", 2016);
    private static final Song SONG_TWO = SongCreator.createSong("Soldier", "Beyonce", "Destiny's Child",
            "pop", 2008);
    private static final Song SONG_THREE = SongCreator.createSong("Silent", "Beyonce", "Hello",
            "pop", 2007);

    private DefaultPlaylistService defaultPlaylistService;
    private PlaylistRepository playlistRepositoryMock;

    @BeforeEach
    void setUp() {
        this.playlistRepositoryMock = Mockito.mock(PlaylistRepository.class);
        this.defaultPlaylistService = new DefaultPlaylistService(playlistRepositoryMock);
    }

    @Test
    void shouldAddPlaylist() {
        //given
        when(playlistRepositoryMock.save(PLAYLIST_ONE)).thenReturn(PLAYLIST_ONE_WITH_ID);

        //when
        long actual = defaultPlaylistService.addPlaylist(PLAYLIST_ONE);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).save(PLAYLIST_ONE),
                () -> assertEquals(1L, actual)
        );
    }

    @Test
    void shouldGetPlaylistById() {
        //given
        when(playlistRepositoryMock.findById(1L)).thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        Playlist actual = defaultPlaylistService.findPlaylistById(1L);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).findById(1L),
                () -> assertEquals(PLAYLIST_ONE_WITH_ID, actual)
        );
    }

    @Test
    void shouldDeletePlaylistById() {
        //when
        defaultPlaylistService.deletePlaylistById(2L);

        //then
        Mockito.verify(playlistRepositoryMock).deleteById(2L);
    }

    @Test
    void shouldGetAllPlaylists() {
        //given
        when(playlistRepositoryMock.findAll()).thenReturn(List.of(PLAYLIST_ONE_WITH_ID));
        List<Playlist> expected = List.of(PLAYLIST_ONE_WITH_ID);

        //when
        List<Playlist> actual = defaultPlaylistService.getPlaylists();

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).findAll(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldAddSongsToPlaylist() {
        //given
        List<Song> songs = List.of(SONG_ONE, SONG_TWO, SONG_THREE);
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Weekend");
        playlist.setSongs(songs);

        when(playlistRepositoryMock.findById(1L)).thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        defaultPlaylistService.addSongsToPlaylist(1L, songs);

        //then
        Mockito.verify(playlistRepositoryMock).save(playlist);
    }

    @Test
    void shouldThrowRuntimeException_whenThereIsNoPlaylistWithThatId() {
        //given
        List<Song> songs = List.of(SONG_ONE, SONG_TWO, SONG_THREE);
        when(playlistRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        //when
        assertThrows(RuntimeException.class, () -> defaultPlaylistService.addSongsToPlaylist(2L, songs));

        //then
        Mockito.verify(playlistRepositoryMock, Mockito.never()).save(Mockito.any());
    }
}
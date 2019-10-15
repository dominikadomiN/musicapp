package com.dominika.service;

import com.dominika.model.Playlist;
import com.dominika.repository.PlaylistRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultPlaylistServiceTest {

    private static final Playlist PLAYLIST_ONE = createPlaylist("Weekend");
    private static final Playlist PLAYLIST_ONE_WITH_ID = createPlaylist(1L, "Weekend");

    private DefaultPlaylistService defaultPlaylistService;
    private PlaylistRepository playlistRepositoryMock;

    @Before
    public void setUp() {
        this.playlistRepositoryMock = Mockito.mock(PlaylistRepository.class);
        this.defaultPlaylistService = new DefaultPlaylistService(playlistRepositoryMock);
    }

    @Test
    public void shouldAddPlaylist() {
        //given
        when(playlistRepositoryMock.save(PLAYLIST_ONE)).thenReturn(PLAYLIST_ONE_WITH_ID);

        //when
        long actual = defaultPlaylistService.addPlaylist(PLAYLIST_ONE);

        //then
        Mockito.verify(playlistRepositoryMock).save(PLAYLIST_ONE);
        assertEquals(1L, actual);

    }

    @Test
    public void shouldGetPlaylistById() {
        //given
        when(playlistRepositoryMock.findById(1L)).thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        Playlist actual = defaultPlaylistService.findPlaylistById(1L);

        //then
        Mockito.verify(playlistRepositoryMock).findById(1L);
        assertEquals(PLAYLIST_ONE_WITH_ID, actual);

    }

    @Test
    public void shouldDeletePlaylistById() {
        //when
        defaultPlaylistService.deletePlaylistById(2L);

        //then
        Mockito.verify(playlistRepositoryMock).deleteById(2L);

    }

    private static Playlist createPlaylist(String playlistName) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistName);

        return playlist;
    }

    private static Playlist createPlaylist(long id, String playlistName) {
        Playlist playlist = new Playlist();
        playlist.setId(id);
        playlist.setName(playlistName);

        return playlist;
    }

}
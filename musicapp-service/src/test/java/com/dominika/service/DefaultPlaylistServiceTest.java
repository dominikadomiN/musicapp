package com.dominika.service;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.Song;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.commons.PlaylistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DefaultPlaylistServiceTest {

    private static final Playlist PLAYLIST_ONE = Playlist.builder()
            .name("Weekend")
            .build();
    private static final Playlist PLAYLIST_ONE_WITH_ID = Playlist.builder()
            .id(1L)
            .name("Weekend")
            .build();
    private static final Song SONG_ONE = Song.builder()
            .name("Hello")
            .interpreter("Beyonce")
            .album("4x4")
            .genre("pop")
            .year(2016)
            .build();
    private static final Song SONG_TWO = Song.builder()
            .name("Soldier")
            .interpreter("Beyonce")
            .album("Destiny's Child")
            .genre("pop")
            .year(2008)
            .build();
    private static final Song SONG_THREE = Song.builder()
            .name("Silent")
            .interpreter("Beyonce")
            .album("Hello")
            .genre("pop")
            .year(2007)
            .build();

    private static final String PLAYLIST_NAME = "Weekend";
    private static final String PLAYLIST_NAME_WHEN_DOESNT_EXIST = "Chill";

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
        when(playlistRepositoryMock.addPlaylist(PLAYLIST_ONE)).thenReturn(PLAYLIST_ONE_WITH_ID.getId());

        //when
        var actual = defaultPlaylistService.addPlaylist(PLAYLIST_ONE);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).addPlaylist(PLAYLIST_ONE),
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldGetPlaylistById() {
        //given
        when(playlistRepositoryMock.getPlaylist(1L)).thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        var actual = defaultPlaylistService.findPlaylistById(1L);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).getPlaylist(1L),
                () -> assertEquals(PLAYLIST_ONE_WITH_ID, actual)
        );
    }

    @Test
    void shouldDeletePlaylistById() {
        //when
        defaultPlaylistService.deletePlaylistById(2L);

        //then
        Mockito.verify(playlistRepositoryMock).deletePlaylist(2L);
    }

    @Test
    void shouldGetAllPlaylists_whenNameIsNull() {
        //given
        when(playlistRepositoryMock.getAllPlaylists()).thenReturn(List.of(PLAYLIST_ONE_WITH_ID));
        var expected = List.of(PLAYLIST_ONE_WITH_ID);

        //when
        List<Playlist> actual = defaultPlaylistService.getPlaylists(null);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).getAllPlaylists(),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldGetAllPlaylists_whenNameIsNotNull() {
        //given
        when(playlistRepositoryMock.findPlaylistsByName(PLAYLIST_NAME)).thenReturn(List.of(PLAYLIST_ONE_WITH_ID));
        var expected = List.of(PLAYLIST_ONE_WITH_ID);

        //when
        List<Playlist> actual = defaultPlaylistService.getPlaylists(PLAYLIST_NAME);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).findPlaylistsByName(PLAYLIST_NAME),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void shouldReturnEmptyOptional_whenPlaylistNameDoesNotExist() {
        //given
        when(playlistRepositoryMock.findPlaylistsByName(PLAYLIST_NAME_WHEN_DOESNT_EXIST)).thenReturn(Collections.emptyList());

        //when
        var actual = defaultPlaylistService.getPlaylists(PLAYLIST_NAME_WHEN_DOESNT_EXIST);

        //then
        assertAll(
                () -> Mockito.verify(playlistRepositoryMock).findPlaylistsByName(PLAYLIST_NAME_WHEN_DOESNT_EXIST),
                () -> assertEquals(Collections.emptyList(), actual)
        );
    }

    @Test
    void shouldAddSongsToPlaylist() {
        //given
        var songs = List.of(SONG_ONE, SONG_TWO, SONG_THREE);
        var playlist = Playlist.builder()
                .id(1L)
                .name("Weekend")
                .songs(songs)
                .build();

        when(playlistRepositoryMock.getPlaylist(1L)).thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        defaultPlaylistService.addSongsToPlaylist(1L, songs);

        //then
        Mockito.verify(playlistRepositoryMock).addPlaylist(playlist);
    }

    @Test
    void shouldThrowNoSuchPlaylistException_whenThereIsNoPlaylistWithThatId() {
        //given
        var songs = List.of(SONG_ONE, SONG_TWO, SONG_THREE);
        when(playlistRepositoryMock.getPlaylist(2L)).thenReturn(Optional.empty());

        //when
        assertThrows(NoSuchPlaylistException.class, () -> defaultPlaylistService.addSongsToPlaylist(2L, songs));

        //then
        Mockito.verify(playlistRepositoryMock, Mockito.never()).addPlaylist(Mockito.any());
    }
}
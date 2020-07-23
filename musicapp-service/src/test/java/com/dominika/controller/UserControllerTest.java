package com.dominika.controller;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;
import com.dominika.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private static final Playlist PLAYLIST_ONE_WITH_ID = Playlist.builder()
            .id(10L)
            .name("Good vibes")
            .build();
    private static final Playlist PLAYLIST_TWO_WITH_ID = Playlist.builder()
            .id(20L)
            .name("Summer")
            .build();
    private static final User USER_ONE = User.builder()
            .id(1L)
            .name("Dominika")
            .playlists(List.of(PLAYLIST_ONE_WITH_ID, PLAYLIST_TWO_WITH_ID))
            .build();
    private static final User USER_TWO = User.builder()
            .id(2L)
            .name("Domi")
            .build();
    private UserService userServiceMock;
    private UserController userController;

    @BeforeEach
    void setUp() {
        this.userServiceMock = Mockito.mock(UserService.class);
        this.userController = new UserController(userServiceMock);
    }

    @Test
    void shouldAddUser() {
        //given
        when(userServiceMock.addUser(USER_ONE)).thenReturn(1L);

        //when
        var actual = userController.addUser(USER_ONE);

        //then
        assertAll(
                () -> Mockito.verify(userServiceMock).addUser(USER_ONE),
                () -> assertEquals(1L, actual)
        );
    }

    @Test
    void shouldDeleteUser() {
        //given,when
        userController.deleteUserById(USER_ONE.getId());

        //then
        Mockito.verify(userServiceMock).deleteUserById(USER_ONE.getId());
    }

    @Test
    void shouldGetPlaylist() {
        //given
        when(userServiceMock.getPlaylist(USER_ONE.getId(), PLAYLIST_ONE_WITH_ID.getId()))
                .thenReturn(Optional.of(PLAYLIST_ONE_WITH_ID));

        //when
        Optional<Playlist> actual = userController.showPlaylistById(USER_ONE.getId(), PLAYLIST_ONE_WITH_ID.getId());

        //then
        assertAll(
                () -> Mockito.verify(userServiceMock).getPlaylist(USER_ONE.getId(), PLAYLIST_ONE_WITH_ID.getId()),
                () -> assertEquals(Optional.of(PLAYLIST_ONE_WITH_ID), actual)
        );
    }

    @Test
    void shouldGetAllPlaylist() {
        //given
        when(userServiceMock.getAllPlaylists(USER_ONE.getId()))
                .thenReturn(List.of(PLAYLIST_ONE_WITH_ID, PLAYLIST_TWO_WITH_ID));

        //when
        List<Playlist> actual = userController.showAllPlaylists(USER_ONE.getId());

        //then
        assertAll(
                () -> Mockito.verify(userServiceMock).getAllPlaylists(USER_ONE.getId()),
                () -> assertEquals(List.of(PLAYLIST_ONE_WITH_ID, PLAYLIST_TWO_WITH_ID), actual)
        );
    }

    @Test
    void shouldReturnEmptyList_whenNoPlaylistsWereFound() {
        //given
        when(userServiceMock.getAllPlaylists(USER_ONE.getId())).thenReturn(Collections.emptyList());

        //when
        List<Playlist> actual = userController.showAllPlaylists(USER_ONE.getId());

        //then
        assertAll(
                () -> Mockito.verify(userServiceMock).getAllPlaylists(USER_ONE.getId()),
                () -> assertEquals(Collections.emptyList(), actual)
        );
    }
}
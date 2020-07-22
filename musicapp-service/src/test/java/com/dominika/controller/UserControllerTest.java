package com.dominika.controller;

import com.dominika.commons.model.Playlist;
import com.dominika.commons.model.User;
import com.dominika.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
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
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldDeleteUser() {
        //given,when
        userController.deleteSongById(USER_ONE.getId());

        //then
        Mockito.verify(userServiceMock).deleteUserById(USER_ONE.getId());
    }
}
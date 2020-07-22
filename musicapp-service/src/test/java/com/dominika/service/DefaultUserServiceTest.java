package com.dominika.service;

import com.dominika.commons.UserRepository;
import com.dominika.commons.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

class DefaultUserServiceTest {
    private static final User USER_ONE = User.builder()
            .name("Domi")
            .build();
    private DefaultUserService defaultUserService;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        this.userRepositoryMock = Mockito.mock(UserRepository.class);
        this.defaultUserService = new DefaultUserService(userRepositoryMock);
    }

    @Test
    void shouldAddUser() {
        //given
        when(userRepositoryMock.addUser(USER_ONE)).thenReturn(USER_ONE.getId());

        //when
        var actual = defaultUserService.addUser(USER_ONE);

        //then
        assertAll(
                () -> Mockito.verify(userRepositoryMock).addUser(USER_ONE),
                () -> Assertions.assertEquals(1L, actual)
        );
    }

    @Test
    void shouldDeleteUser() {
        //given,when
        defaultUserService.deleteUserById(1L);

        //then
        Mockito.verify(userRepositoryMock).deleteUser(1L);
    }
}
package com.dominika.service;

import com.dominika.model.Song;
import com.dominika.repository.SongRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultSongServiceTest {
    private static final Song SONG_ONE = createSong("All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_ONE_WITH_ID = createSong(1L, "All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_TWO_WITH_ID = createSong(2L, "Sorry", "Beyonce", "Lemonade", "Pop", 2016);

    private DefaultSongService defaultSongService;
    private SongRepository songRepositoryMock;

    @Before
    public void setUp() {
        this.songRepositoryMock = Mockito.mock(SongRepository.class);
        this.defaultSongService = new DefaultSongService(songRepositoryMock);
    }

    @Test
    public void shouldAddSong() {
        //given
        when(songRepositoryMock.save(SONG_ONE)).thenReturn(SONG_ONE_WITH_ID);

        //when
        long actual = defaultSongService.addSong(SONG_ONE);

        //then
        Mockito.verify(songRepositoryMock).save(SONG_ONE);
        assertEquals(1L, actual);
    }

    @Test
    public void shouldGetSongs() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs();
        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void findSongById_shouldReturnSong() {
        //given
        when(songRepositoryMock.findById(1L)).thenReturn(Optional.of(SONG_ONE_WITH_ID));
        //when
        Song actual = defaultSongService.findSongById(1L);
        //then
        Mockito.verify(songRepositoryMock).findById(1L);
        assertEquals(SONG_ONE_WITH_ID, actual);
    }

    @Test(expected = RuntimeException.class)
    public void findSongById_shouldThrowRuntimeException() {
        //given
        when(songRepositoryMock.findById(3L)).thenReturn(Optional.empty());

        //when
        defaultSongService.findSongById(3L);
    }

    private static Song createSong(long id, String name, String interpreter, String album, String genre, int year) {
        Song song = createSong(name, interpreter, album, genre, year);
        song.setId(id);

        return song;
    }

    private static Song createSong(String name, String interpreter, String album, String genre, int year) {
        Song song = new Song();
        song.setName(name);
        song.setInterpreter(interpreter);
        song.setAlbum(album);
        song.setGenre(genre);
        song.setYear(year);

        return song;
    }
}
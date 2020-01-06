package com.dominika.service;

import com.dominika.model.Song;
import com.dominika.repository.SongRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import support.SongCreator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultSongServiceTest {
    private static final Song SONG_ONE = SongCreator.createSong("All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_ONE_WITH_ID = SongCreator.createSong(1L, "All Night", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_TWO_WITH_ID = SongCreator.createSong(2L, "Sorry", "Beyonce", "Lemonade", "Pop", 2016);
    private static final Song SONG_THREE_WITH_ID = SongCreator.createSong(3L, "Better Off", "Ariana Grande", "Next", "Pop", 2019);
    private static final Song SONG_FOUR_WITH_ID = SongCreator.createSong(4L, "All Night", "Ariana Grande", "Next", "Pop", 2019);
    private static final Song SONG_FIVE_WITH_ID = SongCreator.createSong(5L, "Hello", "Travis", "123", "Rap", 2017);

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
    public void shouldGetSongs_WithoutParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, null, null, null, null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_NameParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_ONE_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs("All Night", null, null, null, null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_InterpreterParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_THREE_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, "Ariana Grande", null, null, null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_AlbumParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, null, "Next", null, null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_GenreParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_FIVE_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, null, null, "Rap", null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_YearParam() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_FIVE_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, null, null, null, 2017);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSongs_InterpreterAndAlbumParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID, SONG_THREE_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_ONE_WITH_ID, SONG_TWO_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, "Beyonce", "Lemonade", null, null);

        //then
        Mockito.verify(songRepositoryMock).findAll();
        assertEquals(expected, actual);

    }

    @Test
    public void shouldGetSongs_GenreAndYearParams() {
        //given
        when(songRepositoryMock.findAll()).thenReturn(Arrays.asList(SONG_THREE_WITH_ID, SONG_FOUR_WITH_ID, SONG_FIVE_WITH_ID));
        List<Song> expected = Arrays.asList(SONG_FIVE_WITH_ID);

        //when
        List<Song> actual = defaultSongService.getSongs(null, null, null, "Rap", 2017);

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

    @Test
    public void deleteSongById_SoundDeleteSong() {
        //when
        defaultSongService.deleteSongById(2L);

        //then
        Mockito.verify(songRepositoryMock).deleteById(2L);
    }

}
package com.dominika.service;

import com.dominika.commons.model.Song;
import com.dominika.controller.request.SongRequestParams;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.commons.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultSongService implements SongService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSongService.class);
    private final SongRepository songRepository;

    @Autowired
    public DefaultSongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public long addSong(Song song) {
        return songRepository.saveSong(song);
    }

    @Override
    public List<Song> getSongs(SongRequestParams songRequestParams) {
        List<Song> allSongs = songRepository.getAllSongs();

        Stream<Song> songStream = allSongs.stream();
        songStream = filterByStringField(songStream, "name", songRequestParams.getName(), Song::getName);
        songStream = filterByStringField(songStream, "interpreter", songRequestParams.getInterpreter(), Song::getInterpreter);
        songStream = filterByStringField(songStream, "album", songRequestParams.getAlbum(), Song::getAlbum);
        songStream = filterByStringField(songStream, "genre", songRequestParams.getGenre(), Song::getGenre);
        songStream = filterByIntegerField(songStream, "year", songRequestParams.getYear(), Song::getYear);

        List<Song> filteredSongs = songStream.collect(Collectors.toList());
        LOGGER.info("Filtered songs {}", filteredSongs);
        return filteredSongs;
    }

    @Override
    public Song findSongById(long id) {
        Optional<Song> maybeSong = songRepository.getSong(id);
        if (maybeSong.isPresent()) {
            Song song = maybeSong.orElseThrow();
            LOGGER.info("SongEntity with id {} found: {}", id, song);
            return song;
        }
        LOGGER.warn("SongEntity with id {} not found", id);
        throw new NoSuchPlaylistException();
    }

    @Override
    public void deleteSongById(long id) {
        songRepository.deleteSong(id);
        LOGGER.info("SongEntity with id {} deleted", id);
    }


    private Stream<Song> filterByStringField(Stream<Song> songStream,
                                             String fieldName,
                                             String valueToFilter,
                                             Function<Song, String> valueInFunction) {
        if (valueToFilter == null) {
            return songStream;
        }
        LOGGER.info("Filtering {} by {}", fieldName, valueToFilter);
        return songStream.filter(song -> valueInFunction.apply(song).equalsIgnoreCase(valueToFilter));
    }

    private Stream<Song> filterByIntegerField(Stream<Song> songStream,
                                              String fieldName,
                                              Integer valueToFilter,
                                              Function<Song, Integer> valueInFunction) {
        if (valueToFilter == null) {
            return songStream;
        }
        LOGGER.info("Filtering {} by {}", fieldName, valueToFilter);
        return songStream.filter(song -> valueInFunction.apply(song).equals(valueToFilter));
    }
}

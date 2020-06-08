package com.dominika.service;

import com.dominika.controller.request.SongRequestParams;
import com.dominika.controller.validator.NoSuchPlaylistException;
import com.dominika.entity.Song;
import com.dominika.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultSongService implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public DefaultSongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public long addSong(Song song) {
        var savedSong = songRepository.save(song);
        return savedSong.getId();
    }

    @Override
    public List<Song> getSongs(SongRequestParams songRequestParams) {
        List<Song> allSongs = songRepository.findAll();

        Stream<Song> songStream = allSongs.stream();
        songStream = filterByStringField(songStream, songRequestParams.getName(), Song::getName);
        songStream = filterByStringField(songStream, songRequestParams.getInterpreter(), Song::getInterpreter);
        songStream = filterByStringField(songStream, songRequestParams.getAlbum(), Song::getAlbum);
        songStream = filterByStringField(songStream, songRequestParams.getGenre(), Song::getGenre);
        songStream = filterByIntegerField(songStream, songRequestParams.getYear(), Song::getYear);

        return songStream.collect(Collectors.toList());
    }

    @Override
    public Song findSongById(long id) {
        return songRepository.findById(id)
                .orElseThrow(NoSuchPlaylistException::new);
    }

    @Override
    public void deleteSongById(long id) {
        songRepository.deleteById(id);
    }


    private Stream<Song> filterByStringField(Stream<Song> songStream, String valueToFilter, Function<Song, String> valueInFunction) {
        if (valueToFilter == null) {
            return songStream;
        }
        return songStream.filter(song -> valueInFunction.apply(song).equalsIgnoreCase(valueToFilter));
    }

    private Stream<Song> filterByIntegerField(Stream<Song> songStream, Integer valueToFielter, Function<Song, Integer> valueInFunction) {
        if (valueToFielter == null) {
            return songStream;
        }
        return songStream.filter(song -> valueInFunction.apply(song).equals(valueToFielter));
    }
}

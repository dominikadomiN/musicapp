package com.dominika.service;

import com.dominika.model.Song;
import com.dominika.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Song savedSong = songRepository.save(song);
        return savedSong.getId();
    }

    @Override
    public List<Song> getSongs(String name, String interpreter, String album, String genre, Integer year) {
        List<Song> allSongs = songRepository.findAll();

        if (name != null || interpreter != null || album != null || genre != null || year != null) {
            Stream<Song> songStream = allSongs.stream();
            if (name != null) {
                songStream = songStream.filter(song -> song.getName().equalsIgnoreCase(name));
            }

            if (interpreter != null) {
                songStream = songStream.filter(song -> song.getInterpreter().equalsIgnoreCase(interpreter));
            }

            if (album != null) {
                songStream = songStream.filter(song -> song.getAlbum().equalsIgnoreCase(album));
            }

            if (genre != null) {
                songStream = songStream.filter(song -> song.getGenre().equalsIgnoreCase(genre));
            }

            if (year != null) {
                songStream = songStream.filter(song -> song.getYear() == year);
            }

            allSongs = songStream.collect(Collectors.toList());
        }

        return allSongs;
    }

    @Override
    public Song findSongById(long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no such song with id = " + id));
    }

    @Override
    public void deleteSongById(long id) {
        songRepository.deleteById(id);
    }

}

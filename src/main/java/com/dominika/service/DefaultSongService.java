package com.dominika.service;

import com.dominika.model.Song;
import com.dominika.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSongService implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public DefaultSongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Override
    public long addSong(Song song){
        Song savedSong = songRepository.save(song);
        return savedSong.getId();
    }

    @Override
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song findSongById(long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no such song with id = " + id));
    }
}

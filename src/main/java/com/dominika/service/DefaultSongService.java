package com.dominika.service;

import com.dominika.model.Song;
import com.dominika.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

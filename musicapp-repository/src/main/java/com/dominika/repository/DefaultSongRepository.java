package com.dominika.repository;

import com.dominika.commons.SongRepository;
import com.dominika.commons.model.Song;
import com.dominika.repository.entity.SongEntity;
import com.dominika.repository.jpa.SongJpaRepository;
import com.dominika.repository.mapper.SongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultSongRepository implements SongRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSongRepository.class);
    private final SongJpaRepository songJpaRepository;

    @Autowired
    public DefaultSongRepository(SongJpaRepository songJpaRepository) {
        this.songJpaRepository = songJpaRepository;
    }

    @Override
    public long saveSong(Song newSong) {
        SongEntity songEntity = SongMapper.mapToSongEntity(newSong);
        SongEntity savedSong = songJpaRepository.save(songEntity);
        LOGGER.info("SongEntity {} added", songEntity);
        return savedSong.getId();
    }

    @Override
    public void deleteSong(long songId) {
        songJpaRepository.deleteById(songId);
    }

    @Override
    public Optional<Song> getSong(long id) {
        return songJpaRepository.findById(id)
                .map(SongMapper::mapToSong);
    }

    @Override
    public List<Song> getAllSongs() {
        return songJpaRepository.findAll()
                .stream()
                .map(SongMapper::mapToSong)
                .collect(Collectors.toList());
    }
}

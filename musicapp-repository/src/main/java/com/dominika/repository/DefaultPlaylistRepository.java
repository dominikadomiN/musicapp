package com.dominika.repository;

import com.dominika.commons.PlaylistRepository;
import com.dominika.commons.model.Playlist;
import com.dominika.repository.entity.PlaylistEntity;
import com.dominika.repository.jpa.PlaylistJpaRepository;
import com.dominika.repository.mapper.PlaylistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultPlaylistRepository implements PlaylistRepository {
    private final PlaylistJpaRepository playlistJpaRepository;

    @Autowired
    public DefaultPlaylistRepository(PlaylistJpaRepository playlistJpaRepository) {
        this.playlistJpaRepository = playlistJpaRepository;
    }

    @Override
    public long addPlaylist(Playlist playlist) {
        PlaylistEntity playlistEntity = PlaylistMapper.mapToPlaylistEntity(playlist);
        PlaylistEntity savedPlaylist = playlistJpaRepository.save(playlistEntity);

        return savedPlaylist.getId();
    }

    @Override
    public Optional<Playlist> getPlaylist(long id) {
        return playlistJpaRepository.findById(id)
                .map(PlaylistMapper::mapToPlaylist);
    }

    @Override
    public void deletePlaylist(long id) {
        playlistJpaRepository.deleteById(id);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistJpaRepository.findAll()
                .stream()
                .map(PlaylistMapper::mapToPlaylist)
                .collect(Collectors.toList());
    }

    @Override
    public List<Playlist> findPlaylistsByName(String name) {
        return playlistJpaRepository.findByName(name)
                .stream()
                .map(PlaylistMapper::mapToPlaylist)
                .collect(Collectors.toList());
    }
}

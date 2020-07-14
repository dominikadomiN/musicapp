package com.dominika.repository.mapper;

import com.dominika.commons.model.Playlist;
import com.dominika.repository.entity.PlaylistEntity;

import java.util.stream.Collectors;

public final class PlaylistMapper {

    private PlaylistMapper() {
    }

    public static PlaylistEntity mapToPlaylistEntity(Playlist playlist) {
        return PlaylistEntity.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .songs(playlist.getSongs()
                        .stream()
                        .map(SongMapper::mapToSongEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Playlist mapToPlaylist(PlaylistEntity playlistEntity) {
        return Playlist.builder()
                .id(playlistEntity.getId())
                .name(playlistEntity.getName())
                .songs(playlistEntity.getSongs()
                        .stream()
                        .map(SongMapper::mapToSong)
                        .collect(Collectors.toList()))
                .build();
    }
}

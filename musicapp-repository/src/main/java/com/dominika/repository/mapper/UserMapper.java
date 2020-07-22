package com.dominika.repository.mapper;

import com.dominika.commons.model.User;
import com.dominika.repository.entity.PlaylistEntity;
import com.dominika.repository.entity.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {
    }

    public static UserEntity mapToUserEntity(User user) {
        List<PlaylistEntity> playlists = Collections.emptyList();
        if (user.getPlaylists()!=null){
            playlists = user.getPlaylists()
                    .stream()
                    .map(PlaylistMapper::mapToPlaylistEntity)
                    .collect(Collectors.toList());
        }
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .playlists(playlists)
                .build();
    }

    public static User mapToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .playlists(userEntity.getPlaylists()
                        .stream()
                        .map(PlaylistMapper::mapToPlaylist)
                        .collect(Collectors.toList()))
                .build();
    }
}

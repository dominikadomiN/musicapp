package com.dominika.model;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponse {

    private int total;
    private List<Playlist> playlists;
}

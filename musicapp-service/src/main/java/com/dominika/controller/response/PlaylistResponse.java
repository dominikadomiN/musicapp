package com.dominika.controller.response;

import com.dominika.commons.model.Playlist;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponse {

    private int total;
    private List<Playlist> playlists;
}

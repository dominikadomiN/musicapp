package com.dominika.controller;

import com.dominika.model.Playlist;
import com.dominika.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping(value = "/create")
    public long addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.addPlaylist(playlist);
    }
}

package com.dominika.controller;

import com.dominika.model.Playlist;
import com.dominika.model.PlaylistResponse;
import com.dominika.model.Song;
import com.dominika.service.PlaylistService;
import com.dominika.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, SongService songService) {
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @PostMapping(value = "/create")
    public long addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.addPlaylist(playlist);
    }

    @GetMapping(value = "/show/{id}")
    public Playlist showPlaylistById(@PathVariable long id) {
        return playlistService.findPlaylistById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deletePlaylistById(@PathVariable long id) {
        playlistService.deletePlaylistById(id);
    }

    @GetMapping(value = "/show")
    public PlaylistResponse showPlaylist(@RequestParam(required = false) String name) {

        List<Playlist> playlists = playlistService.getPlaylists();
        return mapPlaylistResponse(playlists);
    }

    @PostMapping(value = "/{playlistId}/addSongs")
    public void addSongsToPlaylist(@PathVariable long playlistId,
                                   @RequestBody List<Long> songIds) {
        List<Song> songs = songIds.stream()
                .map(songService::findSongById)
                .collect(Collectors.toList());

        playlistService.addSongsToPlaylist(playlistId, songs);
    }


    private PlaylistResponse mapPlaylistResponse(List<Playlist> playlists) {
        PlaylistResponse playlistResponse = new PlaylistResponse();
        playlistResponse.setPlaylists(playlists);
        playlistResponse.setTotal(playlists.size());
        return playlistResponse;
    }
}

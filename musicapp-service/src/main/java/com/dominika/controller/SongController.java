package com.dominika.controller;

import com.dominika.controller.request.SongRequestParams;
import com.dominika.entity.Song;
import com.dominika.controller.response.SongsResponse;
import com.dominika.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/song")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping(value = "/add")
    public long addSong(@RequestBody Song song) {
        return songService.addSong(song);
    }

    @GetMapping(value = "/show")
    public SongsResponse showSongs(SongRequestParams songRequestParams) {
        var songs = songService.getSongs(songRequestParams);
        return mapSongsResponse(songs);
    }

    @GetMapping(value = "/show/{id}")
    public Song showSongById(@PathVariable long id) {
        return songService.findSongById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteSongById(@PathVariable long id) {
        songService.deleteSongById(id);
    }

    private SongsResponse mapSongsResponse(List<Song> songs) {
        var songsResponse = new SongsResponse();
        songsResponse.setSongs(songs);
        songsResponse.setTotal(songs.size());
        return songsResponse;
    }
}

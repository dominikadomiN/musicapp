package com.dominika.controller;

import com.dominika.model.Song;
import com.dominika.service.DefaultSongService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private DefaultSongService songService;

    @Autowired
    public SongController(DefaultSongService songService) {
        this.songService = songService;
    }

    @PostMapping(value = "/add")
    public long addSong(@RequestBody Song song) {
        return songService.addSong(song);
    }

    @GetMapping(value = "/show")
    public List<Song> showSongs() {
        return songService.getSongs();
    }

    @GetMapping(value = "/show/{id}")
    public Song showSongById(@PathVariable long id) {
        return songService.findSongById(id);
    }
}

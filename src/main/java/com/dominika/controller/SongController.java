package com.dominika.controller;

import com.dominika.model.Song;
import com.dominika.service.DefaultSongService;
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
    public List<Song> showSongs(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String interpreter,
                                @RequestParam(required = false) String album,
                                @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer year) {
        return songService.getSongs(name, interpreter, album, genre, year);
    }

    @GetMapping(value = "/show/{id}")
    public Song showSongById(@PathVariable long id) {
        return songService.findSongById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteSongById(@PathVariable long id) {
        songService.deleteSongById(id);
    }
}

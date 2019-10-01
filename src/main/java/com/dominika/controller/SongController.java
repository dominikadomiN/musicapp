package com.dominika.controller;

import com.dominika.model.Song;
import com.dominika.service.DefaultSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/song")
public class SongController {

    private DefaultSongService songService;

    @Autowired
    public SongController(DefaultSongService songService){
        this.songService = songService;
    }

    @PostMapping(value = "/add")
    public long addSong(@RequestBody Song song){
        return songService.addSong(song);
    }

}

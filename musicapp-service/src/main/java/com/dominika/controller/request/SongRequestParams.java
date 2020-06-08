package com.dominika.controller.request;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
public class SongRequestParams {
    private String name;
    private String interpreter;
    private String album;
    private String genre;
    private Integer year;

    public SongRequestParams(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String interpreter,
                             @RequestParam(required = false) String album,
                             @RequestParam(required = false) String genre,
                             @RequestParam(required = false) Integer year) {
        this.name = name;
        this.interpreter = interpreter;
        this.album = album;
        this.genre = genre;
        this.year = year;
    }
}

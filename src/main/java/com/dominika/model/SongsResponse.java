package com.dominika.model;

import lombok.Data;

import java.util.List;

@Data
public class SongsResponse {

    private int total;
    private List<Song> songs;
}

package com.dominika.controller.response;

import com.dominika.commons.model.Song;
import lombok.Data;

import java.util.List;

@Data
public class SongsResponse {

    private int total;
    private List<Song> songs;
}

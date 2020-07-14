package com.dominika.commons.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode
public class Playlist {
    private long id;
    private String name;
    private List<Song> songs;
}

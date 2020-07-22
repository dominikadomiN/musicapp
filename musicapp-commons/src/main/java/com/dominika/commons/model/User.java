package com.dominika.commons.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class User {
    private long id;
    private String name;
    private List<Playlist> playlists;

}

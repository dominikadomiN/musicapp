package com.dominika.commons.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class Song {
    private long id;
    private String name;
    private String interpreter;
    private String album;
    private String genre;
    private int year;
}

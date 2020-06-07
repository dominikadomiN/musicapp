package com.dominika.controller.validator;

public class NoSuchPlaylistException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "There is no such playlist with given id";

    public NoSuchPlaylistException() {
        this(DEFAULT_MESSAGE);
    }

    public NoSuchPlaylistException(String message) {
        super(message);
    }
}

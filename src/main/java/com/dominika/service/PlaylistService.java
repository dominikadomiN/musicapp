package com.dominika.service;

import com.dominika.model.Playlist;
import org.springframework.stereotype.Service;

public interface PlaylistService {

    long addPlaylist(Playlist playlist);
}

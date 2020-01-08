package support;

import com.dominika.model.Playlist;

public class PlaylistCreator {

    public static Playlist createPlaylist(String playlistName) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistName);

        return playlist;
    }

    public static Playlist createPlaylist(long id, String playlistName) {
        Playlist playlist = new Playlist();
        playlist.setId(id);
        playlist.setName(playlistName);

        return playlist;
    }
}

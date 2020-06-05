CREATE TABLE playlist_song(
	playlist_song_id BIGINT NOT NULL auto_increment PRIMARY KEY,
    song_id BIGINT NOT NULL REFERENCES song(id),
	playlist_id BIGINT NOT NULL REFERENCES playlist(id)
);
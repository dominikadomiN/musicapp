CREATE TABLE IF NOT EXISTS user(
    id BIGINT NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR (200) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_playlist(
    user_playlist_id BIGINT NOT NULL auto_increment PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES user(id),
    playlist_id BIGINT NOT NULL REFERENCES playlist(id)
);
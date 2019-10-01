CREATE TABLE IF NOT EXISTS song(
    id BIGINT NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR (200) NOT NULL,
    interpreter VARCHAR (200) NOT NULL,
    album VARCHAR (200) NOT NULL,
    genre VARCHAR (100) NOT NULL,
    year INT (4) NOT NULL
);

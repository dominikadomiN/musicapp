<b>MusicApp</b>

MusicApp is a web service that enables to add songs and playlists. 
The application used a Model-View-Controller design. The model represents basic entities, such as, Song, Playlist, SongResponse, and PlaylistResponse.
The repository includes SongRepository and PlaylistRepository which extends JpaRepository.
The controller contains endpoints for songs and playlists which enable to monitor and interact with the application.
The service consists of service for both song and playlist in which business logic is held (i.e. adding, getting, finding, and deleting).

<b>Technologies/tools:</b> Java 8, Lombok, Spring Boot, Maven, Hibernate, MySQL

<b>Testing:</b> JUnit4, Mockito

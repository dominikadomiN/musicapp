package com.dominika.repository.jpa;

import com.dominika.commons.model.Playlist;
import com.dominika.repository.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistJpaRepository extends JpaRepository<PlaylistEntity, Long> {
    List<PlaylistEntity> findByName(String name);
}

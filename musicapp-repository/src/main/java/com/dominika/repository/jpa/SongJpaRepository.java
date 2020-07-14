package com.dominika.repository.jpa;

import com.dominika.repository.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongJpaRepository extends JpaRepository<SongEntity, Long> {
}

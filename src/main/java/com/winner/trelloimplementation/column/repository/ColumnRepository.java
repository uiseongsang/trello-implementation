package com.winner.trelloimplementation.column.repository;

import com.winner.trelloimplementation.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    @Query("SELECT MAX(c.position) FROM ColumnEntity c")
    Long findMaxPosition();

    Optional<ColumnEntity> findByPosition(Long currentPosition);

    @Query("SELECT c FROM ColumnEntity c WHERE c.position BETWEEN :currentPosition AND :newPosition ORDER BY c.position")
    List<ColumnEntity> findColumnsBetweenPositions(Long currentPosition, Long newPosition);
}

package com.winner.trelloimplementation.column.repository;

import com.winner.trelloimplementation.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    @Query("SELECT MAX(c.position) FROM ColumnEntity c")
    Long findMaxPosition();
}

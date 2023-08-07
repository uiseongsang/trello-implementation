package com.winner.trelloimplementation.column.repository;

import com.winner.trelloimplementation.column.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
}

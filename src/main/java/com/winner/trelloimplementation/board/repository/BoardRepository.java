package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

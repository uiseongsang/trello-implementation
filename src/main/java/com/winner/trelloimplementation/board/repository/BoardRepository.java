package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
}

package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByUser(User user);
}

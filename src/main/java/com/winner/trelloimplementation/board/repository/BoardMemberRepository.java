package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.entity.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
}

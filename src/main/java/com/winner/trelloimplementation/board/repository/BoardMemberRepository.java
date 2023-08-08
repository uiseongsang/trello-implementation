package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.dto.GetBoardListResponseDto;
import com.winner.trelloimplementation.board.entity.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    BoardMember findByUserIdAndBoardsId(Long userId, Long boardNo);

    List<BoardMember> findByUserId(Long id);
}

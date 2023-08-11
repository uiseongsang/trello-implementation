package com.winner.trelloimplementation.board.repository;

import com.winner.trelloimplementation.board.dto.GetBoardListResponseDto;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    // 보드 멤버를 찾는 메서드 (유저 아이디와 보드 아이디로)
    BoardMember findByUserIdAndBoardsId(Long userId, Long boardNo);
    // 보드 멤버를 유저 아이디로 찾는 메서드
    List<BoardMember> findByUserId(Long id);

    boolean existsByIdAndUserId(Long id, Long id1);
}

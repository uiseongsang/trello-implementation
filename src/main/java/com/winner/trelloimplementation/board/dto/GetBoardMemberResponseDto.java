package com.winner.trelloimplementation.board.dto;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetBoardMemberResponseDto {
    private Long id;
    private MemberRoleEnum role;
    private Long board_id;
    private Long user_id;

    public GetBoardMemberResponseDto (BoardMember boardMember) {
        this.role = boardMember.getRole();
        this.board_id = boardMember.getBoards().getId();
        this.user_id = boardMember.getUser().getId();
    }
}

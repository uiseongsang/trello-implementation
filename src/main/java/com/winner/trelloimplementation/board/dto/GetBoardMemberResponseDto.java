package com.winner.trelloimplementation.board.dto;

import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.user.entity.User;
import lombok.Getter;

@Getter
public class GetBoardMemberResponseDto {
    private Long id;
    private String email;
    private MemberRoleEnum role;

    public GetBoardMemberResponseDto(User user, BoardMember member) {
        this.email = user.getEmail();
        this.role = member.getRole();
    }
}

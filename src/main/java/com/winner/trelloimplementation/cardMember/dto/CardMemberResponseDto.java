package com.winner.trelloimplementation.cardMember.dto;

import com.winner.trelloimplementation.cardMember.entity.CardMember;
import com.winner.trelloimplementation.user.entity.User;
import lombok.Getter;

@Getter
public class CardMemberResponseDto {
    private Long id;
    private String email;

    public CardMemberResponseDto(CardMember cardMember) {
        this.id = cardMember.getId();
        this.email = cardMember.getUser().getEmail();
    }
}

package com.winner.trelloimplementation.cardMember.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardMemberRequestDto {
    private String email;

    @Builder
    public CardMemberRequestDto (String email) {
        this.email = email;
    }
}

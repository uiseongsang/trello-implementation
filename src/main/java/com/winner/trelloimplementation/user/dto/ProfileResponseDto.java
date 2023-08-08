package com.winner.trelloimplementation.user.dto;

import com.winner.trelloimplementation.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String username;
    private String nickname;
    private String email;
    private String introduction;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
    }
}

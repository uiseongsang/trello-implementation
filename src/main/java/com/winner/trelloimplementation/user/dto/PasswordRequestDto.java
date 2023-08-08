package com.winner.trelloimplementation.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordRequestDto {
    private String oldPassword;
    // (?=.*[a-zA-Z])(?=.*\d)
    @Pattern(regexp = "^[a-zA-Z0-9#?!@$%^&*-]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 로 구성되어야 합니다.")
    private String newPassword;
}

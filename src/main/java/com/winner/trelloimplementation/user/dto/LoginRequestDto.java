package com.winner.trelloimplementation.user.dto;

import com.winner.trelloimplementation.user.entity.UserRoleEnum;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    private UserRoleEnum role;
}

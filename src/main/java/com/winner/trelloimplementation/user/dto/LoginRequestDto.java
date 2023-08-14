package com.winner.trelloimplementation.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winner.trelloimplementation.user.entity.UserRoleEnum;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    @JsonIgnore
    private UserRoleEnum role;
}

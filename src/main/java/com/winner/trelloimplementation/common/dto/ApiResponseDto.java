package com.winner.trelloimplementation.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ApiResponseDto {
    private String msg;
    private int status;

    @Builder
    public ApiResponseDto (String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}

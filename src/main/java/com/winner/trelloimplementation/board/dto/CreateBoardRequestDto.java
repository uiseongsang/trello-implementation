package com.winner.trelloimplementation.board.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequestDto {
    private String title;
    private String description;
    private String color;
}

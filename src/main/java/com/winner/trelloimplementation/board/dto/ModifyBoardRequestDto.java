package com.winner.trelloimplementation.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ModifyBoardRequestDto {
    private String title;
    private String description;
    private String color;

    @Builder
    public ModifyBoardRequestDto (String title, String description, String color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }
}

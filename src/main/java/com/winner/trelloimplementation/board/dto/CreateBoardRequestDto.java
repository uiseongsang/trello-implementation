package com.winner.trelloimplementation.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateBoardRequestDto {
    private String title;
    private String description;
    private String color;

    @Builder
    public CreateBoardRequestDto (String title, String description, String color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }
}

package com.winner.trelloimplementation.card.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CardRequestDto {
    private String title;
    private String description;
    private String deadline;
    private String color;

    @Builder
    public CardRequestDto (Long column, String title, String description, String deadline, String color) {
        this.column = column;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.color = color;
    }
}

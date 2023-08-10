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
    public CardRequestDto (String title, String description, String deadline, String color) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.color = color;
    }
}

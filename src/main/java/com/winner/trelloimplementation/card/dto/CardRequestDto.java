package com.winner.trelloimplementation.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto {
    public Long column;
    private String title;
    private String description;
    private String deadline;
    private String color;
}

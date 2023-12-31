package com.winner.trelloimplementation.card.dto;

import com.winner.trelloimplementation.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private String description;
    private String deadline;
    private String color;
    private Long position;
    private Boolean isdeadline;
    private Integer commentSize;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.deadline = card.getDeadline();
        this.color = card.getColor();
        this.position = card.getPosition();
        this.isdeadline = card.isIsdeadline();
        this.commentSize = card.getCommentList().size();
    }
}

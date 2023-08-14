package com.winner.trelloimplementation.card.dto;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.cardMember.dto.CardMemberResponseDto;
import com.winner.trelloimplementation.cardMember.entity.CardMember;
import com.winner.trelloimplementation.comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.comment.entity.Comment;
import com.winner.trelloimplementation.common.entity.TimeStamped;
import com.winner.trelloimplementation.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CardDetailResponseDto {
    private Long id;
    private String title;
    private String description;
    private String deadline;
    private Boolean isdeadline;
    private String color;
    private String createdAt;
    private String modifiedAt;
    private Long columnNo;
    private Long postion;
    private Set<CommentResponseDto> commentList = new LinkedHashSet<>();
    private Set<CardMemberResponseDto> cardMemberList = new LinkedHashSet<>();

    public CardDetailResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.deadline = card.getDeadline();
        this.isdeadline = card.isIsdeadline();
        this.color = card.getColor();
        this.createdAt = card.getCreatedAtAsString();
        this.modifiedAt = card.getModifiedAtAsString();
        this.columnNo = card.getColumnEntity().getId();
        this.postion = card.getPosition();
        for(Comment comment : card.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        for(CardMember cardMember : card.getCardMemberList()) {
            cardMemberList.add(new CardMemberResponseDto(cardMember));
        }
    }
}

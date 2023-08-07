package com.winner.trelloimplementation.Comment.entity;

import com.winner.trelloimplementation.Comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_no")
    private Card card;

    public Comment(CommentRequestDto requestDto, User user, Card card) {
        this.content = requestDto.getContent();
        this.user = user;
        this.card = card;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

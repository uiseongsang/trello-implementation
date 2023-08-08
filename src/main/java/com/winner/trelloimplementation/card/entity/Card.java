package com.winner.trelloimplementation.card.entity;

import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.cardMember.entity.CardMember;
import com.winner.trelloimplementation.comment.entity.Comment;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private Set<Comment> commentList = new LinkedHashSet<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private Set<CardMember> cardMemberList = new LinkedHashSet<>();

    public Card(CardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
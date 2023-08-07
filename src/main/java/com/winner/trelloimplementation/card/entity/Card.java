package com.winner.trelloimplementation.card.entity;

import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

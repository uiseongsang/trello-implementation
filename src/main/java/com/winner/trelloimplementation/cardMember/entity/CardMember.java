package com.winner.trelloimplementation.cardMember.entity;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card_members")
public class CardMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_no")
    private Card card;

    public CardMember(User user, Card card) {
        this.user = user;
        this.card = card;
    }
}

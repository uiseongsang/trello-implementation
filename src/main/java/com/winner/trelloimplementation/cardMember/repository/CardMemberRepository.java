package com.winner.trelloimplementation.cardMember.repository;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.cardMember.entity.CardMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardMemberRepository extends JpaRepository<CardMember, Long> {
    Optional<CardMember> findCardMemberByCardAndUserEmail(Card card, String email);
}

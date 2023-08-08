package com.winner.trelloimplementation.card.repository;

import com.winner.trelloimplementation.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}

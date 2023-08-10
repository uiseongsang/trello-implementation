package com.winner.trelloimplementation.card.repository;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<List<Card>> findByColumnEntity(ColumnEntity column);
    Optional<Card> findByColumnEntityAndId(ColumnEntity column, Long id);

    Optional<List<Card>> findByDeadlineIsNotNullAndIsdeadlineFalse();
}

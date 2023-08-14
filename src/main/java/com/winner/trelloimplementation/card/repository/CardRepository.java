package com.winner.trelloimplementation.card.repository;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<List<Card>> findByColumnEntity(ColumnEntity column);
    Optional<Card> findByColumnEntityAndId(ColumnEntity column, Long id);

    Optional<Card> findByColumnEntityAndPosition(ColumnEntity column, Long id);
    Optional<List<Card>> findByDeadlineIsNotNullAndIsdeadlineFalse();

    @Query("SELECT c FROM Card c WHERE c.columnEntity = :column AND c.position BETWEEN :currentPosition AND :newPosition ORDER BY c.position")
    List<Card> findCardsBetweenPositionsByColumn(@Param("currentPosition")Long position, @Param("newPosition") Long changePositionNo, @Param("column") ColumnEntity column);
}

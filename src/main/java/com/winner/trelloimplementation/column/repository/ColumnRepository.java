package com.winner.trelloimplementation.column.repository;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    @Query("SELECT MAX(c.position) FROM ColumnEntity c WHERE c.boards = :board")
    Long findMaxPositionByBoard(@Param("board") Board board);

//    Optional<ColumnEntity> findByPosition(Long currentPosition);

//    @Query("SELECT c FROM ColumnEntity c WHERE c.position BETWEEN :currentPosition AND :newPosition ORDER BY c.position")
//    List<ColumnEntity> findColumnsBetweenPositions(Long currentPosition, Long newPosition);


    @Query("SELECT c FROM ColumnEntity c WHERE c.boards = :board")
    List<ColumnEntity> findAllByBoard(@Param("board") Board board);

    Optional<ColumnEntity> findByBoardsAndPosition(Board board, Long currentPosition);



    @Query("SELECT c FROM ColumnEntity c WHERE c.boards = :board AND c.position BETWEEN :currentPosition AND :newPosition ORDER BY c.position")
    List<ColumnEntity> findColumnsBetweenPositionsByBoard(@Param("currentPosition") Long currentPosition, @Param("newPosition") Long newPosition, @Param("board") Board board);

}

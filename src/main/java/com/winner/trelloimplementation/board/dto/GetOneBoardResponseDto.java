package com.winner.trelloimplementation.board.dto;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetOneBoardResponseDto {
    private Long id;
    private String title;
    private String color;
    private List<ColumnEntity> columns = new ArrayList<>();

    public GetOneBoardResponseDto (Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.columns = board.getColumns();
    }
}

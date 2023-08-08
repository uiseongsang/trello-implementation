package com.winner.trelloimplementation.board.dto;

import com.winner.trelloimplementation.board.entity.Board;
import lombok.Getter;

@Getter
public class GetBoardListResponseDto {
    private Long id;
    private String title;

    public GetBoardListResponseDto (Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }
}

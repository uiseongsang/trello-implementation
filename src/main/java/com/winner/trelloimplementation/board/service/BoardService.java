package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.user.entity.User;

public interface BoardService {

    /**
     * Board 생성 기능
     * @param createBoardRequestDto 생성할 보드 정보 (title, description, color)
     */
    void createBoard (User user, CreateBoardRequestDto createBoardRequestDto);
}
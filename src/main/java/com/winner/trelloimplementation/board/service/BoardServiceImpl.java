package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl (BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void createBoard(CreateBoardRequestDto createBoardRequestDto) {

        // createBoard에서 받아온 정보로 Board 생성
        Board board = new Board (createBoardRequestDto.getTitle(), createBoardRequestDto.getDescription(), createBoardRequestDto.getColor());
        // board 저장
        boardRepository.save(board);
    }
}

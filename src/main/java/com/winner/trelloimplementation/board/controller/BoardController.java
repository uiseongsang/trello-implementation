package com.winner.trelloimplementation.board.controller;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    public BoardController (BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }

    @PostMapping ("/board")
    public ResponseEntity<ApiResponseDto> createBoard (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody CreateBoardRequestDto createBoardRequestDto) {
        try {
            boardServiceImpl.createBoard(userDetailsImpl.getUser(), createBoardRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDto("Board 생성 성공", HttpStatus.CREATED.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}

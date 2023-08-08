package com.winner.trelloimplementation.board.controller;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.dto.GetBoardListResponseDto;
import com.winner.trelloimplementation.board.dto.GetOneBoardResponseDto;
import com.winner.trelloimplementation.board.dto.ModifyBoardRequestDto;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping ("/board/{boardNo}")
    public ResponseEntity<ApiResponseDto> modifyBoard (@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ModifyBoardRequestDto modifyBoardRequestDto, @PathVariable Long boardNo) {
        try {
            boardServiceImpl.modifyBoard(userDetails.getUser(), modifyBoardRequestDto, boardNo);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponseDto("Board 수정 성공", HttpStatus.ACCEPTED.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping ("/board/{boardNo}")
    public ResponseEntity<ApiResponseDto> deleteBoard (@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardNo) {
        try {
            boardServiceImpl.deleteBoard(userDetails.getUser(), boardNo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto("Board 삭제 성공", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping ("/boards/{boardNo}")
    public GetOneBoardResponseDto getOneBoard (@PathVariable Long boardNo) {
        return boardServiceImpl.getOneBoard(boardNo);
    }

    @GetMapping ("/boards")
    public List<GetBoardListResponseDto> getBoardList () {
        return boardServiceImpl.getBoardList();
    }
}

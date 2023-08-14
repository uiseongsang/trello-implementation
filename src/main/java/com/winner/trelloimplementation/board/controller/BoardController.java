package com.winner.trelloimplementation.board.controller;

import com.winner.trelloimplementation.board.dto.*;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public GetOneBoardResponseDto getOneBoard (@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardNo) {
        return boardServiceImpl.getOneBoard(userDetails.getUser(), boardNo);
    }

    @GetMapping ("/boards")
    public List<GetBoardListResponseDto> getBoardList (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardServiceImpl.getBoardList(userDetailsImpl.getUser());
    }

    @PostMapping ("/board/{boardNo}/invitation")
    public ResponseEntity<ApiResponseDto> sendEmailToInviteUser (@PathVariable Long boardNo, @RequestBody EmailRequestDto emailRequestDto) {
        try {
            boardServiceImpl.sendEmailToInviteUser(boardNo, emailRequestDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto(emailRequestDto.getEmail() +  " 초대 완료", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    @GetMapping("/board/{username}")
    public Long getUserIdFromUsername (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable String username) {
        return boardServiceImpl.getUserIdFromUsername(userDetailsImpl.getUser(), username);
    }

    @GetMapping("/board/getMembers")
    public List<GetBoardMemberResponseDto> getBoardMembers (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardServiceImpl.getBoardMember(userDetailsImpl.getUser());
    }

    @GetMapping("/boardTitle/{boardTitle}")
    public Long getBoardIdFromBoardTitle (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable String boardTitle) {
        return boardServiceImpl.getBoardIdFromBoardTitle(userDetailsImpl.getUser(), boardTitle);
    }

}

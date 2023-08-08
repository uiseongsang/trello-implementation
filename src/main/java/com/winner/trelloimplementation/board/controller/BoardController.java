package com.winner.trelloimplementation.board.controller;

import com.winner.trelloimplementation.board.dto.*;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
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
    public GetOneBoardResponseDto getOneBoard (@PathVariable Long boardNo) {
        return boardServiceImpl.getOneBoard(boardNo);
    }

    @GetMapping ("/boards")
    public List<GetBoardListResponseDto> getBoardList () {
        return boardServiceImpl.getBoardList();
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

//    @GetMapping ("/board/invitation/{boardNo}")
//    public void checkUserInfo (@PathVariable Long boardNo, @RequestParam("email") String email) {
//        // 우선 해당 유저가 존재하는지 확인
//        // 존재한다면 -> 해당 이메일을 가진 유저를 BoardMember에 추가 -> 로그인하도록 redirect
//        // 존재하지 않는다면 -> 회원가입하도록 (이 부분은 따로 만들어야 할 수도) -> 회원가입 후에 해당 이메일을 가진 유저를 BoardMember에 추가
//        boardServiceImpl.checkUserInfo(boardNo, email);
//    }
}

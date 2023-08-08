package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.dto.ModifyBoardRequestDto;
import com.winner.trelloimplementation.user.entity.User;

public interface BoardService {

    /**
     * 보드 생성
     * @param user 생성할 유저 (=현재 유저)
     * @param createBoardRequestDto 요청 정보
     */
    void createBoard (User user, CreateBoardRequestDto createBoardRequestDto);

    /**
     * 보드 수정
     * @param user 현재 유저
     * @param modifyBoardRequestDto 요청 정보
     * @param boardNo 수정할 보드 아이디
     */
    void modifyBoard(User user, ModifyBoardRequestDto modifyBoardRequestDto, Long boardNo);

    /**
     * 보드 삭제
     * @param user 현재 유저
     * @param boardNo 삭제할 보드 아이디
     */
    void deleteBoard(User user, Long boardNo);

    /**
     * 보드 읽기
     * @param boardNo 읽고자 하자 보드
     */
//    void getOneBoard(Long boardNo);
}

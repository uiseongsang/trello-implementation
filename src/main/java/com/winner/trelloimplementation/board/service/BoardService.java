package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.*;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
     * @param boardNo 읽고자 하는 보드 아이디
     * @return GetOneBoardResponseDto
     */
    GetOneBoardResponseDto getOneBoard(Long boardNo);

    /**
     * 보드 리스트 읽기
     * @return 보드 리스트
     */
    List<GetBoardListResponseDto> getBoardList();

    /**
     * 이메일 전송 기능
     * @param boardNo 전송할 때 줄 보드 아이디
     * @param emailRequestDto 보낼 이메일 주소
     */
    void sendEmailToInviteUser(Long boardNo, EmailRequestDto emailRequestDto) throws MessagingException, UnsupportedEncodingException;

}

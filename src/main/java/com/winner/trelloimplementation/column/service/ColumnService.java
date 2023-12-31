package com.winner.trelloimplementation.column.service;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.dto.GetOneColumnResponseDto;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.user.entity.User;

public interface ColumnService {

    /**
     * 컬럼을 생성
     *
     * @param boardNo      컬럼을 생성할 보드 숫자
     * @param lastPosition 마지막 위치에 넣기 위해 null값을 전달
     * @param requestDto   생성 할 title
     * @param user         생성할 유저
     */
    void create(Long boardNo, Long lastPosition, ColumnRequestDto requestDto, User user);


    /**
     * 컬럼을 수정
     *
     * @param columnNo   수정할 컬럼 숫자
     * @param requestDto 수정할 title
     * @param user       수정할 유저
     */
    void update(Long columnNo, ColumnRequestDto requestDto, User user);

    /**
     * 컬럼 삭제
     *
     * @param columnNo 삭제할 컬럼 숫자
     */
    void delete(Long columnNo);

    /**
     * 컬럼 이동
     *
     * @param boardNo         컬럼을 이동할 보드 숫자
     * @param currentPosition 현재 포지션
     * @param newPosition     이동 할 포지션
     */
    void move(Long boardNo, Long currentPosition, Long newPosition);

    /**
     * 컬럼 단건 조회
     *
     * @param columnNo 조회할 컬럼 숫자
     * @return 컬럼 정보
     */
    GetOneColumnResponseDto getOneColumn(Long columnNo);

    /**
     * 유저 검사
     *
     * @param user 찾고자 하는 유저
     */
    void checkUser(User user);

    /**
     * 컬럼 넘버를 통해 컬럼 찾기
     *
     * @param columnNo 찾을 컬럼
     * @return 찾은 컬럼
     */
    ColumnEntity findColumnEntity(Long columnNo);

    /**
     * 보드 넘버를 통해 보드 찾기
     *
     * @param boardNo 찾을 보드
     * @return 찾은 보드
     */
    Board findBoard(Long boardNo);

    /**
     * 해당 보드의 컬럼의 포지션 찾기
     *
     * @param board 찾을 컬럼의 board
     * @param columnPosition 찾을 컬럼의 포지션
     * @return 찾은 컬럼
     */
    ColumnEntity findColumnEntitybyBoardAndPosition(Board board,Long columnPosition);
}

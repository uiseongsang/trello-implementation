package com.winner.trelloimplementation.column.service;

import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.user.entity.User;

public interface ColumnService {

    /**
     * 컬럼을 생성합니다
     *
     * @param lastPosition 마지막 위치에 넣기 위해 null값을 전달
     * @param requestDto   title
     * @param user         유저 사용정보
     */
    void create(Long lastPosition, ColumnRequestDto requestDto, User user);

//    /**
//     *
//     * @param columnNo
//     * @param newPosition
//     */
//    void move(Long columnNo, Long newPosition);
}

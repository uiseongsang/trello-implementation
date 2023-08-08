package com.winner.trelloimplementation.column.service;

import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.column.repository.ColumnRepository;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;

    private final UserRepository userRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository, UserRepository userRepository) {
        this.columnRepository = columnRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Long lastPosition, ColumnRequestDto requestDto, User user) {

        userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("해당 회원이 존재하지 않습니다.")
        );

        ColumnEntity column = new ColumnEntity(requestDto.getTitle());

        // 컬럼 생성시 원하는 포지션값인 desiredPosition 값을 사용하여 position 설정
        if (lastPosition == null) {
            // 위치를 안 정해 졌을 경우 가장 마지막 위치에 넣기
            Long checkLastPosition = columnRepository.findMaxPosition();
            column.setPosition(checkLastPosition != null ? checkLastPosition + 1 : 1);
        }

        columnRepository.save(column);
    }

//    @Override
//    public void move(Long columnNo, Long newPosition) {
//
//    }
}

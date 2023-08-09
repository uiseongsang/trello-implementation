package com.winner.trelloimplementation.column.service;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.dto.GetOneColumnResponseDto;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.column.repository.ColumnRepository;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.columnRepository = columnRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void create(Long boardNo, Long lastPosition, ColumnRequestDto requestDto, User user) {

        User userInfo = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 회원이 존재하지 않습니다.")
        );

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("해당 보드가 존재하지 않습니다.")
        );

        ColumnEntity column = new ColumnEntity(requestDto.getTitle());

        // 컬럼 생성시 원하는 포지션값인 desiredPosition 값을 사용하여 position 설정
        if (lastPosition == null) {
            // 위치를 안 정해 졌을 경우 가장 마지막 위치에 넣기
            Long checkLastPosition = columnRepository.findMaxPositionByBoard(board);
            column.setPosition(checkLastPosition != null ? checkLastPosition + 1 : 1);
        }

        column.addBoard(board);

        columnRepository.save(column);
    }

    @Override
    @Transactional
    public void update(Long columnNo, ColumnRequestDto requestDto, User user) {
        checkUser(user);

        ColumnEntity column = findColumnEntity(columnNo);

        column.update(requestDto);
    }

    @Override
    @Transactional
    public void delete(Long columnNo) {
        ColumnEntity column = findColumnEntity(columnNo);

        Long deletedPosition = column.getPosition();
        Long boardNo = column.getBoards().getId();
        Board board = findBoard(boardNo);

        columnRepository.delete(column);

        List<ColumnEntity> remainedColumns = columnRepository.findAllByBoard(board);
        for (ColumnEntity remainedColumn : remainedColumns) {
            log.info(remainedColumn.getTitle());
            Long currentPosition = remainedColumn.getPosition();
            if (currentPosition > deletedPosition) {
                remainedColumn.setPosition(currentPosition - 1);
            }
        }
    }

    @Override
    @Transactional
    public void move(Long boardNo, Long currentPosition, Long newPosition) {
        Board board = findBoard(boardNo);

        if (currentPosition.equals(newPosition)) {
            return;
        }

        // 바꿀 첫번쨰 컬럼 포지션 예외처리
        ColumnEntity column = columnRepository.findByBoardsAndPosition(board, currentPosition).orElseThrow(
                () -> new NullPointerException("선택한 컬럼이가 존재하지 않습니다.")
        );
        // 바꿀 두번쨰 컬럼 포지션 예외처리: 컬럼이 1,2,3있을 떄 1 컬럼을 존재하지 않는 4에 move할 때 예외처리
        columnRepository.findByBoardsAndPosition(board, newPosition).orElseThrow(
                () -> new NullPointerException("선택한 컬럼이가 존재하지 않습니다.")
        );

        // setPosion할떄 하나씩 -1을 해줄지 +1를 해줄지 알려주는 디렉션 변수
        int direction = currentPosition.compareTo(newPosition);

        if (direction < 0) {
            // 포지션 하나씩 감소
            List<ColumnEntity> columnsToUpdate = columnRepository.findColumnsBetweenPositionsByBoard(currentPosition, newPosition, board);
            for (ColumnEntity c : columnsToUpdate) {
                c.setPosition(c.getPosition() - 1);
            }
        } else if (direction > 0) {
            // 포지션 하나씩 증가
            List<ColumnEntity> columnsToUpdate = columnRepository.findColumnsBetweenPositionsByBoard(newPosition, currentPosition, board);
            for (ColumnEntity c : columnsToUpdate) {
                c.setPosition(c.getPosition() + 1);
            }
        }

        // 이동된 컬럼 업데이트
        column.setPosition(newPosition);
    }

    @Override
    public GetOneColumnResponseDto getOneColumn(Long columnNo) {
        ColumnEntity columnEntity= findColumnEntity(columnNo);
        //Board board = findBoard(columnEntity.getBoards().getId());

        return new GetOneColumnResponseDto(columnEntity);
    }

    @Override
    public void checkUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("해당 회원이 존재하지 않습니다.")
        );
    }

    @Override
    public ColumnEntity findColumnEntity(Long columnNo) {
        return columnRepository.findById(columnNo).orElseThrow(
                () -> new NullPointerException("선택한 컬럼이 존재하지 않습니다.")
        );
    }

    @Override
    public Board findBoard(Long boardNo) {
        return boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );
    }
}

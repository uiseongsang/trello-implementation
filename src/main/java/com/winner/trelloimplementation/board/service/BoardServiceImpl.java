package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.dto.GetBoardListResponseDto;
import com.winner.trelloimplementation.board.dto.GetOneBoardResponseDto;
import com.winner.trelloimplementation.board.dto.ModifyBoardRequestDto;
import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.board.repository.BoardMemberRepository;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;

    public BoardServiceImpl (BoardRepository boardRepository, UserRepository userRepository, BoardMemberRepository boardMemberRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.boardMemberRepository = boardMemberRepository;
    }

    @Override
    public void createBoard(User user, CreateBoardRequestDto createBoardRequestDto) {

        userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("회원이 존재하지 않습니다.")
        );

        // createBoard 받아온 정보로 Board 생성
        Board board = new Board(createBoardRequestDto.getTitle(), createBoardRequestDto.getDescription(), createBoardRequestDto.getColor());

        BoardMember member = new BoardMember(user, board, MemberRoleEnum.ADMIN);

        board.addMember(member);

        board.addUser(user);

        boardRepository.save(board);

        boardMemberRepository.save(member);

    }

    @Override
    @Transactional
    public void modifyBoard(User user, ModifyBoardRequestDto modifyBoardRequestDto, Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        if (!board.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아닙니다.");
        }

        board.update(modifyBoardRequestDto);
    }

    @Override
    @Transactional
    public void deleteBoard(User user, Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        if (!board.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아닙니다.");
        }

        boardRepository.delete(board);
    }

    @Override
    public GetOneBoardResponseDto getOneBoard(Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        return new GetOneBoardResponseDto(board);
    }

    @Override
    public List<GetBoardListResponseDto> getBoardList() {

        return boardRepository.findAll().stream().map(GetBoardListResponseDto::new).toList();
    }
}

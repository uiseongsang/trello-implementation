package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.*;
import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.board.repository.BoardMemberRepository;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // Base64 Encode 한 SecretKey
    private String owner;

    public BoardServiceImpl (BoardRepository boardRepository, UserRepository userRepository, BoardMemberRepository boardMemberRepository, JavaMailSender javaMailSender) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.javaMailSender = javaMailSender;
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

    @Override
    public void sendEmailToInviteUser(Long boardNo, EmailRequestDto emailRequestDto) throws MessagingException, UnsupportedEncodingException {
        String receiverMail = emailRequestDto.getEmail();

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, receiverMail);// 보내는 대상
        message.setSubject("Trello 회원가입 이메일 인증");// 제목

        String boardName = boardRepository.findById(boardNo).get().getTitle();

        String body = "<div>"
                + "<h1> 안녕하세요. Trello 관리자입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 초대된 보드로 이동합니다.<p>"
                + "<a href='http://localhost:8080/api/board/" + boardNo + "'>" + boardName + "</a>"
                + "</div>";

        message.setText(body, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress(owner, "ADMIN"));// 보내는 사람

        javaMailSender.send(message); // 메일 전송
    }
}
